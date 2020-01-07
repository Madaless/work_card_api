package com.madaless.work_cards_api.dtos;


import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProjectDto {

  private Long id;
  @NotNull
  @Size(min = 5, max = 20, message = "Name length must be between 5 and 20")
  private String name;
  @NotNull
  @Size(min = 2, max = 255, message = "Name length must be between 2 and 255")
  private String description;
  private Set<NestedEmployeeDto> employeesAssigned;
  private WorkCardDto workCard;
}
