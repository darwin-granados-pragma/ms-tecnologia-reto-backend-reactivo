package co.com.tecnologia.model.technology.capacity;

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
public class CapacityTechnology {

  private String id;
  private String idTechnology;
  private String idCapacity;
}
