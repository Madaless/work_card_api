package com.madaless.work_cards_api.mapper;

import com.madaless.work_cards_api.dtos.NestedEmployeeDto;
import com.madaless.work_cards_api.entities.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SimpleEmployeeMapper {

  NestedEmployeeDto toDto(Employee employee);

}
