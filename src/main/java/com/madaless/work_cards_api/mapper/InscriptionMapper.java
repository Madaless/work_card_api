package com.madaless.work_cards_api.mapper;

import com.madaless.work_cards_api.dtos.InscriptionDto;
import com.madaless.work_cards_api.entities.Inscription;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InscriptionMapper {

  InscriptionDto toDto(Inscription inscription);

  Inscription toEntity(InscriptionDto inscriptionDto);

  List<InscriptionDto> inscriptionToInscriptionDto(List<Inscription> inscriptionList);

}
