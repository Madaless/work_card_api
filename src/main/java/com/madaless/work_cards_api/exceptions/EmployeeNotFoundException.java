package com.madaless.work_cards_api.exceptions;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
      super("Employee with given id not found : " + id);
  }
}
