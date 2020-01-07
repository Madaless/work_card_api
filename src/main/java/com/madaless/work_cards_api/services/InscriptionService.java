package com.madaless.work_cards_api.services;

import com.madaless.work_cards_api.dtos.InscriptionDto;
import com.madaless.work_cards_api.entities.Inscription;
import com.madaless.work_cards_api.exceptions.InscriptionNotFoundException;
import com.madaless.work_cards_api.mapper.InscriptionMapper;
import com.madaless.work_cards_api.repositories.InscriptionRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InscriptionService {

  private final InscriptionRepository inscriptionRepository;
  private final InscriptionMapper inscriptionMapper;

  public InscriptionService(
      InscriptionRepository inscriptionRepository, InscriptionMapper inscriptionMapper) {
    this.inscriptionRepository = inscriptionRepository;
    this.inscriptionMapper = inscriptionMapper;
  }

  public InscriptionDto save(InscriptionDto inscriptionDto) {
    return inscriptionMapper
        .toDto(inscriptionRepository.save(inscriptionMapper.toEntity(inscriptionDto)));
  }

  public void deleteInscriptionById(Long id) {
    inscriptionRepository.delete(
        inscriptionRepository.findById(id).orElseThrow(() -> new InscriptionNotFoundException(id)));
  }

  public InscriptionDto findInscriptionById(final Long id) {
    return inscriptionMapper.toDto(
        inscriptionRepository.findById(id).orElseThrow(() -> new InscriptionNotFoundException(id)));
  }

  public List<InscriptionDto> findInscriptionByProjectId(Long projectID) {
    List<InscriptionDto> list = inscriptionMapper
        .inscriptionToInscriptionDto(inscriptionRepository.findInscriptionsByProjectId(projectID));
    if (list == null || list.size() == 0) {
      throw new InscriptionNotFoundException(projectID);
    }
    return list;
  }

  public List<InscriptionDto> getInscriptionByEmployeeId(Long id){
    return inscriptionMapper.inscriptionToInscriptionDto(inscriptionRepository.getInscriptionByEmployeeId(id));
  }

  public List<Inscription> getInscriptionEByEmployeeId(Long id) {
    return inscriptionRepository.getInscriptionByEmployeeId(id);
  }

}
