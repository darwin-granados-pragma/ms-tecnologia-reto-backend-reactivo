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
  TECHNOLOGY_NOT_FOUND("TECHNOLOGY-NOT-FOUND",
      ExceptionCode.NOT_FOUND,
      "No se encontró la tecnologia con id: "
  ),
  CAPACITY_NOT_FOUND("CAPACITY-NOT-FOUND",
      ExceptionCode.NOT_FOUND,
      "No se encontró la capacidad con id: "
  );

  private final String fullErrorCode;
  private final ExceptionCode exceptionCode;
  private final String message;
}
