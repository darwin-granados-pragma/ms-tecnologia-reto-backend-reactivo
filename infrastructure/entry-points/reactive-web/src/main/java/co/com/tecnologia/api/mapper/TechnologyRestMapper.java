package co.com.tecnologia.api.mapper;

import co.com.tecnologia.api.model.request.TechnologyCreateRequest;
import co.com.tecnologia.api.model.response.TechnologyResponse;
import co.com.tecnologia.model.technology.Technology;
import co.com.tecnologia.model.technology.TechnologyCreate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface TechnologyRestMapper {

  TechnologyCreate toTechnologyCreate(TechnologyCreateRequest request);

  TechnologyResponse toTechnologyResponse(Technology technology);
}
