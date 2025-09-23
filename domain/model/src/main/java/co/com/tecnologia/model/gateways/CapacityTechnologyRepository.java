package co.com.tecnologia.model.gateways;

import co.com.tecnologia.model.technology.capacity.CapacityTechnology;
import reactor.core.publisher.Mono;

public interface CapacityTechnologyRepository {

  Mono<CapacityTechnology> save(CapacityTechnology data);
}
