package com.madaless.work_cards_api.exceptions;

public class InscriptionNotFoundException extends RuntimeException {

    public InscriptionNotFoundException(Long id) {
      super("Inscriptions not found for project id : " + id);
  }
}
