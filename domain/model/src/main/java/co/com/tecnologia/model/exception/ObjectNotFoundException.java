package co.com.tecnologia.model.exception;

import co.com.tecnologia.model.error.ErrorCode;

public class ObjectNotFoundException extends ApplicationException {
  public ObjectNotFoundException(ErrorCode errorCode, String value) {
    super(errorCode, value);
  }

}
