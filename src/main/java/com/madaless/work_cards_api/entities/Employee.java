package com.madaless.work_cards_api.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "employees")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;
  private String lastName;
  private String phoneNumber;
  private BigDecimal totalHours = new BigDecimal(0);

  @ManyToMany(fetch = FetchType.LAZY,cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
  })
  @JoinTable(
      name = "employees_projects",
      joinColumns = @JoinColumn(name = "employees_assigned_id"),
      inverseJoinColumns = @JoinColumn(name = "projects_id"))
  private Set<Project> projects = new HashSet<>();


}
