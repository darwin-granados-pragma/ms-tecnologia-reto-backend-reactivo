package co.com.tecnologia.model.gateways;

import co.com.tecnologia.model.technology.Technology;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TechnologyRepository {
  Mono<Technology> save(Technology technology);

  Mono<Boolean> existsById(String id);

  Flux<Technology> findTechnologiesByIdCapacity(String idCapacity);

  Mono<Void> deleteById(String id);
}
