package com.madaless.work_cards_api.mapper;

import com.madaless.work_cards_api.dtos.EmployeeDto;
import com.madaless.work_cards_api.entities.Employee;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

  EmployeeDto toDto(Employee employee);

  Employee toEntity(EmployeeDto employeeDto);

  List<EmployeeDto> employeesToEmployeesDto(List<Employee> employeeList);


}
