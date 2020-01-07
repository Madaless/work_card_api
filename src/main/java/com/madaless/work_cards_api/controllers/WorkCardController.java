package com.madaless.work_cards_api.controllers;

import com.madaless.work_cards_api.dtos.WorkCardDto;
import com.madaless.work_cards_api.services.WorkCardService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("work_cards")
public class WorkCardController {

  private final WorkCardService workCardService;

  public WorkCardController(WorkCardService workCardService) {
    this.workCardService = workCardService;
  }


  @PostMapping
  public String addWorkCard(@RequestBody WorkCardDto workCard) {
    workCardService.save(workCard);
    return "Work Card added";
  }

  @GetMapping
  public List<WorkCardDto> getAllWorkCards() {
    return workCardService.getAllWorkCards();
  }
}
