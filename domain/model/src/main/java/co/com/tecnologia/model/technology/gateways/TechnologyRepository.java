package co.com.tecnologia.model.technology.gateways;

import co.com.tecnologia.model.technology.Technology;
import reactor.core.publisher.Mono;

public interface TechnologyRepository {
  Mono<Technology> save(Technology technology);

  Mono<Void> deleteAll();
}
