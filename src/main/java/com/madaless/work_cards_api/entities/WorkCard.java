package com.madaless.work_cards_api.entities;

import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "work_cards")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class WorkCard {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private BigDecimal totalHours = new BigDecimal(0);
  @OneToMany
  @JoinColumn(name = "work_card_id")
  private Set<Inscription> inscriptions;

}
