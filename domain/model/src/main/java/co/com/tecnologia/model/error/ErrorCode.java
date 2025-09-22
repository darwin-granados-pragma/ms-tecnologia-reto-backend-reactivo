package co.com.tecnologia.model.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  TECHNOLOGY_NAME_ALREADY_EXISTS("TECHNOLOGY-NAME-ALREADY-EXISTS",
      ExceptionCode.CONSTRAINT_VIOLATION,
      "El nombre de la tecnologia ya está registrado."
  ),
  CONSTRAINT_VIOLATION("CONSTRAINT-VIOLATION",
      ExceptionCode.CONSTRAINT_VIOLATION,
      "Violación de restricción de datos."
  ),
  ;

  private final String fullErrorCode;
  private final ExceptionCode exceptionCode;
  private final String message;
}
