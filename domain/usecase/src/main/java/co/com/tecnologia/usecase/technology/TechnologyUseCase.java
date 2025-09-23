package co.com.tecnologia.usecase.technology;

import co.com.tecnologia.model.technology.Technology;
import co.com.tecnologia.model.technology.TechnologyCreate;
import co.com.tecnologia.model.technology.gateways.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TechnologyUseCase {

  private final TechnologyRepository repository;

  public Mono<Technology> createTechnology(TechnologyCreate data) {
    return Mono
        .fromCallable(() -> Technology
            .builder()
            .name(data.name())
            .description(data.description())
            .build())
        .flatMap(repository::save);
  }
}
