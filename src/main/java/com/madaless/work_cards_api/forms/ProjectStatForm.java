package com.madaless.work_cards_api.forms;

import com.madaless.work_cards_api.entities.Project;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectStatForm {

  private Long id;
  private String projectName;
  private BigDecimal totalHours;

  public ProjectStatForm(Project project) {
    this.id = project.getId();
    this.projectName = project.getName();
    this.totalHours = project.getWorkCard().getTotalHours();
  }

}
