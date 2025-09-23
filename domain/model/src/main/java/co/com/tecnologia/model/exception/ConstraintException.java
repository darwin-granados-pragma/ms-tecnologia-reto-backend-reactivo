package co.com.tecnologia.model.exception;

import co.com.tecnologia.model.error.ErrorCode;

public class ConstraintException extends ApplicationException {

  public ConstraintException(ErrorCode errorCode) {
    super(errorCode);
  }
}
