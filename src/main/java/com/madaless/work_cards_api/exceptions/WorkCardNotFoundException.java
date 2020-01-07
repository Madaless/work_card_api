package com.madaless.work_cards_api.exceptions;

public class WorkCardNotFoundException extends RuntimeException {

    public WorkCardNotFoundException(Long id) {
      super("Work Card with given id not found : " + id);
  }
}
