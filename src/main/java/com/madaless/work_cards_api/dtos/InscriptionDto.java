package com.madaless.work_cards_api.dtos;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
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
public class InscriptionDto {

  private Long id;
  @NotNull
  @Size(min = 2, max = 255, message = "Name length must be between 2 and 255")
  private String description;
  private Date inscriptionDate = new Date();
  @DecimalMin(value = "0.0", inclusive = false)
  @Digits(integer=7, fraction=2)
  @NotNull
  private BigDecimal hoursSpend;
  private NestedEmployeeDto employee;
}
