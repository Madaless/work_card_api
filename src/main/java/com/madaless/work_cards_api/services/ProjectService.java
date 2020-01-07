package com.madaless.work_cards_api.services;

import com.madaless.work_cards_api.dtos.NestedProjectDto;
import com.madaless.work_cards_api.dtos.ProjectDto;
import com.madaless.work_cards_api.entities.Employee;
import com.madaless.work_cards_api.entities.Project;
import com.madaless.work_cards_api.exceptions.ProjectNotFoundException;
import com.madaless.work_cards_api.forms.ProjectStatForm;
import com.madaless.work_cards_api.mapper.ProjectMapper;
import com.madaless.work_cards_api.mapper.SimpleProjectMapper;
import com.madaless.work_cards_api.repositories.ProjectRepository;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper;
  private final SimpleProjectMapper simpleProjectMapper;

  public ProjectService(
      ProjectRepository projectRepository, ProjectMapper projectMapper,
      SimpleProjectMapper simpleProjectMapper) {
    this.projectRepository = projectRepository;
    this.projectMapper = projectMapper;
    this.simpleProjectMapper = simpleProjectMapper;
  }

  public List<NestedProjectDto> getAllProjects() {
    return simpleProjectMapper.projectsToProjectsDto(projectRepository.findAll());
  }

  public void deleteProject(ProjectDto projectDto) {
    projectRepository.delete(projectMapper.toEntity(projectDto));
  }

  public ProjectDto findProjectById(final Long id) {
    return projectMapper
        .toDto(projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id)));
  }

  public ProjectDto save(ProjectDto projectDto) {
    return projectMapper.toDto(projectRepository.save(projectMapper.toEntity(projectDto)));
  }

  public Project saveE(Project project) {
    return projectRepository.save(project);
  }

  public List<ProjectStatForm> getStat() {
    List<ProjectStatForm> projectStatFormList = new LinkedList<>();
    List<Project> projectList = projectRepository.findAll();
    for (Project project : projectList) {
      if (project.getWorkCard() != null) {
        projectStatFormList.add(new ProjectStatForm(project));
      }
    }
    return projectStatFormList;
  }

  public void removeProjectsForEmployee(Long id) {
    Project project = projectRepository.findById(id)
        .orElseThrow(() -> new ProjectNotFoundException(id));

    for (final Employee emp : project.getEmployeesAssigned()) {
      emp.getProjects().remove(project);
    }
  }
}
