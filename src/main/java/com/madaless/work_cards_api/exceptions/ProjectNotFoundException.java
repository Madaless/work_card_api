package com.madaless.work_cards_api.exceptions;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(Long id) {
      super("Project with given id not found : " + id);
  }
}
