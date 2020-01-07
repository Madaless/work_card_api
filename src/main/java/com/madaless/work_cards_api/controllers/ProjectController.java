package com.madaless.work_cards_api.controllers;

import com.madaless.work_cards_api.dtos.EmployeeDto;
import com.madaless.work_cards_api.dtos.InscriptionDto;
import com.madaless.work_cards_api.dtos.NestedEmployeeDto;
import com.madaless.work_cards_api.dtos.NestedProjectDto;
import com.madaless.work_cards_api.dtos.ProjectDto;
import com.madaless.work_cards_api.dtos.WorkCardDto;
import com.madaless.work_cards_api.entities.Employee;
import com.madaless.work_cards_api.entities.Project;
import com.madaless.work_cards_api.exceptions.EmployeeNotFoundException;
import com.madaless.work_cards_api.exceptions.InscriptionNotFoundException;
import com.madaless.work_cards_api.exceptions.ProjectNotFoundException;
import com.madaless.work_cards_api.exceptions.WorkCardNotFoundException;
import com.madaless.work_cards_api.forms.ProjectStatForm;
import com.madaless.work_cards_api.mapper.EmployeeMapper;
import com.madaless.work_cards_api.mapper.ProjectMapper;
import com.madaless.work_cards_api.mapper.SimpleEmployeeMapper;
import com.madaless.work_cards_api.services.EmployeeService;
import com.madaless.work_cards_api.services.InscriptionService;
import com.madaless.work_cards_api.services.ProjectService;
import com.madaless.work_cards_api.services.WorkCardService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("projects")
public class ProjectController {

  private final ProjectService projectService;
  private final EmployeeService employeeService;
  private final WorkCardService workCardService;
  private final InscriptionService inscriptionService;
  private final ProjectMapper projectMapper;
  private final EmployeeMapper employeeMapper;
  private final SimpleEmployeeMapper simpleEmployeeMapper;

  public ProjectController(
      ProjectService projectService, EmployeeService employeeService,
      WorkCardService workCardService, InscriptionService inscriptionService,
      ProjectMapper projectMapper, EmployeeMapper employeeMapper,
      SimpleEmployeeMapper simpleEmployeeMapper) {
    this.projectService = projectService;
    this.employeeService = employeeService;
    this.workCardService = workCardService;
    this.inscriptionService = inscriptionService;
    this.projectMapper = projectMapper;
    this.employeeMapper = employeeMapper;
    this.simpleEmployeeMapper = simpleEmployeeMapper;
  }

  @PostMapping
  public ResponseEntity<ProjectDto> saveProject(@Valid @RequestBody final ProjectDto project) {
    ProjectDto pro = projectService.save(project);
    return new ResponseEntity<>(pro, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProjectDto> editProject(@PathVariable(value = "id") final Long id,
      @Valid @RequestBody final ProjectDto editedProject) throws ProjectNotFoundException {
    if (editedProject.getId() == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    projectService.findProjectById(id);
    return new ResponseEntity<>(projectService.save(editedProject), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProjectDto> getProject(@PathVariable final Long id)
      throws ProjectNotFoundException {
    ProjectDto project = projectService.findProjectById(id);
    return new ResponseEntity<>(project, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ProjectDto> deleteProject(@PathVariable final Long id)
      throws ProjectNotFoundException {

    projectService.removeProjectsForEmployee(id);

    final ProjectDto projectDto = projectService.findProjectById(id);
    projectDto.getEmployeesAssigned().clear();
    projectService.deleteProject(projectDto);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Project deleted", String.valueOf(id));
    return new ResponseEntity<>(headers, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<NestedProjectDto>> getAllProjects() {
    HttpHeaders headers = new HttpHeaders();
    List<NestedProjectDto> projects = projectService.getAllProjects();
    headers.add("Number Of Records Found", String.valueOf(projects.size()));
    return new ResponseEntity<>(projects, headers, HttpStatus.OK);
  }

  @PostMapping("/{proId}/assign_employee/{empId}")
  public ResponseEntity<EmployeeDto> assignEmpToProject(@PathVariable final Long empId,
      @PathVariable final Long proId) throws EmployeeNotFoundException, ProjectNotFoundException {
    final Employee emp = employeeMapper.toEntity(employeeService.findEmployeeById(empId));
    final Project pro = projectMapper.toEntity(projectService.findProjectById(proId));

    if(emp.getProjects().stream().anyMatch(t -> t.getId().equals(pro.getId())) )
      return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
    pro.getEmployeesAssigned().add(emp);
    projectService.saveE(pro);
    emp.getProjects().add(pro);
    employeeService.saveE(emp);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("{proId}/work_card")
  public ResponseEntity<WorkCardDto> getWorkCardOfProject(@PathVariable final Long proId)
      throws WorkCardNotFoundException, ProjectNotFoundException {
    ProjectDto projectDto = projectService.findProjectById(proId);
    WorkCardDto workCardDto = projectDto.getWorkCard();

    return new ResponseEntity<>(workCardDto, HttpStatus.OK);
  }

  @PostMapping("{proId}/work_card")
  public ResponseEntity<WorkCardDto> addWorkCard(@PathVariable final Long proId,
      @Valid @RequestBody final WorkCardDto workCardDto) throws ProjectNotFoundException {
    final ProjectDto project = projectService.findProjectById(proId);
    project.setWorkCard(workCardDto);
    projectService.save(project);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("{proId}/work_card/{wcId}")
  public ResponseEntity<ProjectDto> changeWorkCard(@PathVariable Long proId,
      @PathVariable Long wcId)
      throws ProjectNotFoundException, WorkCardNotFoundException {
    ProjectDto projectDto = projectService.findProjectById(proId);
    projectDto.setWorkCard(workCardService.findWorkCardById(wcId));
    projectService.save(projectDto);
    return new ResponseEntity<>(projectDto, HttpStatus.OK);
  }

  @PostMapping("{proId}/employees/{empId}/inscriptions")
  public String addInscription(@PathVariable final Long proId,
      @Valid @RequestBody final InscriptionDto inscriptionDto,
      @PathVariable final Long empId)
      throws ProjectNotFoundException, EmployeeNotFoundException, WorkCardNotFoundException {

    EmployeeDto employeeDto = employeeService.findEmployeeById(empId);
    ProjectDto projectDto = projectService.findProjectById(proId);
    if (projectDto.getEmployeesAssigned().contains(
        new NestedEmployeeDto(employeeDto.getId(), employeeDto.getFirstName(),
            employeeDto.getLastName()))) {
      employeeDto.addTimeFromInscription(inscriptionDto.getHoursSpend());
      inscriptionDto.setEmployee(
          simpleEmployeeMapper.toDto(employeeMapper.toEntity(employeeService.save(employeeDto))));
      InscriptionDto inscriptionDto1 = inscriptionService.save(inscriptionDto);
      WorkCardDto workCardDto = projectDto.getWorkCard();
      workCardDto.getInscriptions().add(inscriptionDto1);
      workCardDto.addTimeFromInscription(inscriptionDto1.getHoursSpend());
      projectService.save(projectDto);
      return "inscription added";
    } else {
      return "employee is not assigned to project";
    }
  }

  @PutMapping("{proId}/inscriptions/{insId}")
  public ResponseEntity<InscriptionDto> editInscription(@PathVariable Long proId,
      @PathVariable Long insId, @Valid @RequestBody InscriptionDto editedInscriptionDto)
      throws InscriptionNotFoundException, ProjectNotFoundException {
    if (!editedInscriptionDto.getId().equals(insId)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    if (editedInscriptionDto.getId() == null) {
      editedInscriptionDto.setId(insId);
    }

    if (editedInscriptionDto.getEmployee() == null) {
      editedInscriptionDto.setEmployee(inscriptionService.findInscriptionById(insId).getEmployee());
    }

    ProjectDto projectDto = projectService.findProjectById(proId);
    projectDto.getWorkCard().setTotalHours(
        projectService.findProjectById(proId).getWorkCard().getTotalHours()
            .subtract(inscriptionService.findInscriptionById(insId).getHoursSpend())
            .add(editedInscriptionDto.getHoursSpend()));
    projectService.save(projectDto);
    inscriptionService.findInscriptionById(insId);
    return new ResponseEntity<>(inscriptionService.save(editedInscriptionDto), HttpStatus.OK);
  }

  @DeleteMapping("{proId}/inscriptions/{insId}")
  public ResponseEntity<InscriptionDto> deleteInscription(@PathVariable Long proId,
      @PathVariable Long insId)
      throws InscriptionNotFoundException, ProjectNotFoundException {
    ProjectDto projectDto = projectService.findProjectById(proId);
    projectDto.getWorkCard().setTotalHours(
        projectService.findProjectById(proId).getWorkCard().getTotalHours()
            .subtract(inscriptionService.findInscriptionById(insId).getHoursSpend()));
    EmployeeDto emp = employeeService
        .findEmployeeById(inscriptionService.findInscriptionById(insId).getEmployee().getId());

    emp.minusTimeFromInscription(inscriptionService.findInscriptionById(insId).getHoursSpend());
    employeeService.save(emp);
    projectService.save(projectDto);
    inscriptionService.deleteInscriptionById(insId);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("{proId}/inscriptions")
  public ResponseEntity<List<InscriptionDto>> getInscriptions(@PathVariable final Long proId)
      throws InscriptionNotFoundException {
    HttpHeaders headers = new HttpHeaders();
    List<InscriptionDto> list = inscriptionService.findInscriptionByProjectId(proId);
    headers.add("Number of inscriptions", String.valueOf(list.size()));
    return new ResponseEntity<>(list, headers, HttpStatus.OK);
  }

  @GetMapping("/statistics")
  public ResponseEntity<List<ProjectStatForm>> getStatistics() {
    HttpHeaders headers = new HttpHeaders();
    List<ProjectStatForm> projectStatFormList;
    projectStatFormList = projectService.getStat();
    headers.add("Number of projects", String.valueOf(projectStatFormList.size()));
    return new ResponseEntity<>(projectStatFormList, headers, HttpStatus.OK);
  }

}
