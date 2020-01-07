package com.madaless.work_cards_api.mapper;

import com.madaless.work_cards_api.dtos.NestedProjectDto;
import com.madaless.work_cards_api.entities.Project;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SimpleProjectMapper {

  NestedProjectDto toDto(Project project);

  List<NestedProjectDto> projectsToProjectsDto(List<Project> projectList);

}
