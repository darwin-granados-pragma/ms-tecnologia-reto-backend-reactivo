package co.com.tecnologia.api.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CapacityTechnologyCreateRequest {

  @NotBlank(message = "El identificador de la capacidad es obligatorio")
  @Size(max = 50, message = "El identificador de la capacidad debe tener menos de 50 caracteres")
  private String idCapacity;

  @NotNull(message = "Las tecnologías son obligatorias")
  private Set<String> idTechnologies;
}
