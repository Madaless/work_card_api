package com.madaless.work_cards_api.repositories;

import com.madaless.work_cards_api.entities.WorkCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkCardRepository extends JpaRepository<WorkCard, Long> {

}
