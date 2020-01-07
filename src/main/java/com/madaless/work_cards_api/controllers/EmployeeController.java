package com.madaless.work_cards_api.controllers;

import com.madaless.work_cards_api.dtos.EmployeeDto;
import com.madaless.work_cards_api.dtos.InscriptionDto;
import com.madaless.work_cards_api.entities.Inscription;
import com.madaless.work_cards_api.exceptions.EmployeeNotFoundException;
import com.madaless.work_cards_api.forms.Rapport;
import com.madaless.work_cards_api.services.EmployeeService;
import com.madaless.work_cards_api.services.InscriptionService;
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
@RequestMapping("employees")
public class EmployeeController {

  private final EmployeeService employeeService;
  private final InscriptionService inscriptionService;

  public EmployeeController(
      EmployeeService employeeService, InscriptionService inscriptionService) {
    this.employeeService = employeeService;
    this.inscriptionService = inscriptionService;
  }

  @PostMapping()
  public ResponseEntity<EmployeeDto> addEmployee(@RequestBody @Valid EmployeeDto emp) {
    return new ResponseEntity<>(employeeService.save(emp), HttpStatus.CREATED);
  }

  @PutMapping("{id}")
  public ResponseEntity<EmployeeDto> editEmployee(@RequestBody @Valid EmployeeDto emp, @PathVariable Long id) throws EmployeeNotFoundException {
    employeeService.findEmployeeById(id);
    if(emp.getId() == null)
      emp.setId(id);
    return new ResponseEntity<>(employeeService.save(emp), HttpStatus.OK);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<EmployeeDto> deleteEmployee(@PathVariable final Long id)
      throws EmployeeNotFoundException {
    employeeService.findEmployeeById(id);
    for (Inscription i : inscriptionService.getInscriptionEByEmployeeId(id)) {
      i.setEmployee(null);
    }
    HttpHeaders headers = new HttpHeaders();
    headers.add("Employee deleted", String.valueOf(id));
    employeeService.deleteEmployeeById(id);
    return new ResponseEntity<>(headers, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
    return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id)
      throws EmployeeNotFoundException {
    return new ResponseEntity<>(employeeService.findEmployeeById(id), HttpStatus.OK);
  }

  @GetMapping("{empId}/rapport")
  public Rapport generateRapport(@PathVariable Long empId) {
    return employeeService.prepareRapport(empId);
  }

  @GetMapping("/rapports")
  public ResponseEntity<List<Rapport>> generateRapports() {
    return new ResponseEntity<>(employeeService.prepareRapports(), HttpStatus.OK);
  }

}
