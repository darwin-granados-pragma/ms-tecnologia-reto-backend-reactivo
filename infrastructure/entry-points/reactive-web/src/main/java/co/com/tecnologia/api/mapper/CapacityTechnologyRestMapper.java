package co.com.tecnologia.api.mapper;

import co.com.tecnologia.model.technology.capacity.CapacityTechnologyCreate;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CapacityTechnologyRestMapper {

  CapacityTechnologyCreate toCapacityTechnologyCreate(String idCapacity, Set<String> technologies);
}
