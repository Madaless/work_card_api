package com.madaless.work_cards_api.mapper;

import com.madaless.work_cards_api.dtos.ProjectDto;
import com.madaless.work_cards_api.entities.Project;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

  ProjectDto toDto(Project project);

  Project toEntity(ProjectDto projectDto);

  List<ProjectDto> projectsToProjectsDto(List<Project> projectList);

}
