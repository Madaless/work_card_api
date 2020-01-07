package com.madaless.work_cards_api.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "inscriptions")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Inscription {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;
  private Date inscriptionDate = new Date();
  private BigDecimal hoursSpend;

  @ManyToOne
  private Employee employee;

}
