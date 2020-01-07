package com.madaless.work_cards_api.services;

import com.madaless.work_cards_api.dtos.WorkCardDto;
import com.madaless.work_cards_api.exceptions.WorkCardNotFoundException;
import com.madaless.work_cards_api.mapper.WorkCardMapper;
import com.madaless.work_cards_api.repositories.WorkCardRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WorkCardService {

  private final WorkCardRepository workCardRepository;
  private final WorkCardMapper workCardMapper;

  public WorkCardService(
      WorkCardRepository workCardRepository, WorkCardMapper workCardMapper) {
    this.workCardRepository = workCardRepository;
    this.workCardMapper = workCardMapper;
  }

  public List<WorkCardDto> getAllWorkCards() {
    return workCardMapper.workCardsToWorkCardsDto(workCardRepository.findAll());
  }

  public WorkCardDto save(WorkCardDto workCardDto) {
    return workCardMapper.toDto(workCardRepository.save(workCardMapper.toEntity(workCardDto)));
  }

  public WorkCardDto findWorkCardById(final Long id) {
    return workCardMapper.toDto(
        workCardRepository.findById(id).orElseThrow(() -> new WorkCardNotFoundException(id)));
  }

}
