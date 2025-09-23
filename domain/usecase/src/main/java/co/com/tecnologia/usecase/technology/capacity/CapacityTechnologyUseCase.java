package co.com.tecnologia.usecase.technology.capacity;

import co.com.tecnologia.model.gateways.CapacityTechnologyRepository;
import co.com.tecnologia.model.gateways.TransactionalGateway;
import co.com.tecnologia.model.technology.capacity.CapacityTechnology;
import co.com.tecnologia.model.technology.capacity.CapacityTechnologyCreate;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CapacityTechnologyUseCase {

  private final CapacityTechnologyRepository repository;
  private final TransactionalGateway transactionalGateway;

  public Mono<Void> assignTechnologiesToCapacity(CapacityTechnologyCreate data) {
    return transactionalGateway.execute(getAssociationsToSave(data).then());
  }

  private Flux<CapacityTechnology> getAssociationsToSave(CapacityTechnologyCreate data) {
    return Flux
        .fromIterable(data.technologies())
        .map(idTechnology -> CapacityTechnology
            .builder()
            .idCapacity(data.idCapacity())
            .idTechnology(idTechnology)
            .build())
        .flatMap(repository::save);
  }
}
