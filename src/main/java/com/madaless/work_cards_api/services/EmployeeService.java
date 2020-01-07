package com.madaless.work_cards_api.services;

import com.madaless.work_cards_api.dtos.EmployeeDto;
import com.madaless.work_cards_api.dtos.NestedEmployeeDto;
import com.madaless.work_cards_api.entities.Employee;
import com.madaless.work_cards_api.exceptions.EmployeeNotFoundException;
import com.madaless.work_cards_api.forms.Rapport;
import com.madaless.work_cards_api.mapper.EmployeeMapper;
import com.madaless.work_cards_api.mapper.SimpleEmployeeMapper;
import com.madaless.work_cards_api.repositories.EmployeeRepository;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final EmployeeMapper employeeMapper;
  private final SimpleEmployeeMapper simpleEmployeeMapper;

  public EmployeeService(
      EmployeeRepository employeeRepository, EmployeeMapper employeeMapper,
      SimpleEmployeeMapper simpleEmployeeMapper) {
    this.employeeRepository = employeeRepository;
    this.employeeMapper = employeeMapper;
    this.simpleEmployeeMapper = simpleEmployeeMapper;
  }

  public List<EmployeeDto> getAllEmployees() {
    return employeeMapper.employeesToEmployeesDto(employeeRepository.findAll());
  }

  public EmployeeDto save(EmployeeDto employeeDto) {
    return employeeMapper.toDto(employeeRepository.save(employeeMapper.toEntity(employeeDto)));
  }

  public Employee saveE(Employee employee) {
    return employeeRepository.save(employee);
  }

  public void deleteEmployeeById(Long id) {
    employeeRepository.deleteById(id);
  }

  public EmployeeDto findEmployeeById(final Long id) {
    return employeeMapper
        .toDto(
            employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id)));
  }

  public Rapport prepareRapport(Long empId) {
    Employee employee = employeeRepository.findById(empId)
        .orElseThrow(() -> new EmployeeNotFoundException(empId));
    NestedEmployeeDto ns = simpleEmployeeMapper.toDto(employee);
    Rapport rapport = new Rapport(ns, new LinkedList<>());
    rapport.prepareRapport(employee);
    return rapport;
  }

  public List<Rapport> prepareRapports() {
    List<Rapport> rapports = new ArrayList<>();
    for (Employee employee : employeeRepository.findAll()) {
      rapports.add(prepareRapport(employee.getId()));
    }

    return rapports;
  }

}
