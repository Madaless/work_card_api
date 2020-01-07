package com.madaless.work_cards_api.dtos;

import java.math.BigDecimal;
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
public class WorkCardDto {

  private Long id;
  @NotNull
  @Size(min = 2, max = 20, message = "Name length must be between 2 and 20")
  private String name;
  private Set<InscriptionDto> inscriptions;
  private BigDecimal totalHours = new BigDecimal(0);

  public void addTimeFromInscription(BigDecimal time) {
    totalHours = totalHours.add(time);
  }
}
