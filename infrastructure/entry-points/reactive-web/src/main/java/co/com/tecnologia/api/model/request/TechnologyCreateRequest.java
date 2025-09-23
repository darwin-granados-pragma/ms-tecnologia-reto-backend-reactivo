package co.com.tecnologia.api.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class TechnologyCreateRequest {

  @NotBlank(message = "El nombre es obligatorio")
  @Size(max = 50, message = "El nombre debe tener menos de 50 caracteres")
  private String name;

  @NotBlank(message = "La descripción es obligatoria")
  @Size(max = 90, message = "La descripción debe tener menos de 90 caracteres")
  private String description;
}
