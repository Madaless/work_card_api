package com.madaless.work_cards_api.repositories;

import com.madaless.work_cards_api.entities.Inscription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

  @Query("SELECT i from Project p JOIN p.workCard wc JOIN wc.inscriptions i WHERE p.id = :projectID")
  List<Inscription> findInscriptionsByProjectId(Long projectID);

  List<Inscription> getInscriptionByEmployeeId(Long empId);
}
