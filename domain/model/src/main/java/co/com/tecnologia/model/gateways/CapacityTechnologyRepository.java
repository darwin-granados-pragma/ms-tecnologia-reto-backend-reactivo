package co.com.tecnologia.model.gateways;

import co.com.tecnologia.model.technology.capacity.CapacityTechnology;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CapacityTechnologyRepository {

  Mono<CapacityTechnology> save(CapacityTechnology data);

  Flux<CapacityTechnology> findAllByIdCapacity(String idCapacity);

  Mono<Void> deleteAll(List<CapacityTechnology> capacityTechnologyList);

}
