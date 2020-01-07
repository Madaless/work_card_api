package com.madaless.work_cards_api.forms;

import com.madaless.work_cards_api.dtos.NestedEmployeeDto;
import com.madaless.work_cards_api.entities.Employee;
import com.madaless.work_cards_api.entities.Inscription;
import com.madaless.work_cards_api.entities.Project;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rapport {

  private NestedEmployeeDto employee;
  private List<RapportRow> rapportRows;

  public void prepareRapport(Employee... employees) {
    for (Employee e : employees) {
      for (Project p : e.getProjects()) {
        BigDecimal sum = new BigDecimal(0);
        if(p.getWorkCard() !=  null) {
          for (Inscription i : p.getWorkCard().getInscriptions()) {
            sum = sum.add(i.getHoursSpend());
          }
          rapportRows.add(new RapportRow(p.getName(), sum));
        }
      }
    }
  }

}
