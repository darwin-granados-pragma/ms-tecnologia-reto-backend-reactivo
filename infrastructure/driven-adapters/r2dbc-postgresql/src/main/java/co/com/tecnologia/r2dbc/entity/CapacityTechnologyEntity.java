package co.com.tecnologia.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "capacidad_tecnologias")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CapacityTechnologyEntity {

  @Id
  @Column("id_capacidad_tecnologia")
  private String id;

  @Column("id_tecnologia")
  private String idTechnology;

  @Column("id_capacidad")
  private String idCapacity;
}
