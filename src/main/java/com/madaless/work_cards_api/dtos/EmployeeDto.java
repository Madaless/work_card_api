package com.madaless.work_cards_api.dtos;

import java.math.BigDecimal;
import java.util.HashSet;
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
public class EmployeeDto {

  private Long id;
  @NotNull
  @Size(min = 2, max = 30, message = "Name length must be between 3 and 30")
  private String firstName;
  @NotNull
  @Size(min = 2, max = 30, message = "Last name length must be between 3 and 30")
  private String lastName;
  @NotNull
  @Size(min = 7, max = 10, message = "Name length must be between 7 and 10")
  private String phoneNumber;
  private BigDecimal totalHours = new BigDecimal(0);
  private Set<NestedProjectDto> projects = new HashSet<>();

  public void addTimeFromInscription(BigDecimal time) {
    totalHours = totalHours.add(time);
  }
  public void minusTimeFromInscription(BigDecimal time) {
    totalHours = totalHours.subtract(time);
  }

}
