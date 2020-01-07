package com.madaless.work_cards_api.mapper;

import com.madaless.work_cards_api.dtos.WorkCardDto;
import com.madaless.work_cards_api.entities.WorkCard;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkCardMapper {

  WorkCardDto toDto(WorkCard workCard);

  WorkCard toEntity(WorkCardDto workCardDto);

  List<WorkCardDto> workCardsToWorkCardsDto(List<WorkCard> workCardList);

}
