package co.com.tecnologia.usecase.technology;

import co.com.tecnologia.model.error.ErrorCode;
import co.com.tecnologia.model.exception.ObjectNotFoundException;
import co.com.tecnologia.model.gateways.CapacityTechnologyRepository;
import co.com.tecnologia.model.gateways.TechnologyRepository;
import co.com.tecnologia.model.gateways.TransactionalGateway;
import co.com.tecnologia.model.technology.Technology;
import co.com.tecnologia.model.technology.TechnologyCreate;
import co.com.tecnologia.model.technology.capacity.CapacityTechnology;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TechnologyUseCase {

  private final TechnologyRepository repository;
  private final CapacityTechnologyRepository capacityTechnologyRepository;
  private final TransactionalGateway transactionalGateway;

  public Mono<Technology> createTechnology(TechnologyCreate data) {
    return Mono
        .fromCallable(() -> Technology
            .builder()
            .name(data.name())
            .description(data.description())
            .build())
        .flatMap(repository::save);
  }

  public Mono<Void> validateTechnologies(Set<String> technologies) {
    return Flux
        .fromIterable(technologies)
        .flatMap(this::validateTechnologyById)
        .then();
  }

  public Flux<Technology> findTechnologiesByIdCapacity(String idCapacity) {
    return repository
        .findTechnologiesByIdCapacity(idCapacity)
        .switchIfEmpty(Mono.error(new ObjectNotFoundException(ErrorCode.CAPACITY_NOT_FOUND,
            idCapacity
        )));
  }

  public Mono<Void> deleteCapacityAndRelationsWithTechnologies(String idCapacity) {
    return getCapacityTechnologies(idCapacity)
        .collectList()
        .flatMap(this::deleteAllRelations);
  }

  private Mono<Void> validateTechnologyById(String id) {
    return repository
        .existsById(id)
        .flatMap(exists -> Boolean.TRUE.equals(exists) ? Mono.empty()
            : Mono.error(new ObjectNotFoundException(ErrorCode.TECHNOLOGY_NOT_FOUND, id)));
  }

  private Mono<Void> deleteAllRelations(List<CapacityTechnology> capacityTechnologyList) {
    if (capacityTechnologyList.isEmpty()) {
      return Mono.empty();
    }
    Mono<Void> deleteCapacityTechnologies = capacityTechnologyRepository
        .deleteAll(capacityTechnologyList)
        .then()
        .as(transactionalGateway::execute);

    Mono<Void> deleteTechnologies = Flux
        .fromIterable(capacityTechnologyList)
        .flatMap(relation -> repository.deleteById(relation.getIdTechnology()))
        .then();

    return deleteCapacityTechnologies.then(deleteTechnologies);
  }

  private Flux<CapacityTechnology> getCapacityTechnologies(String idCapacity) {
    return capacityTechnologyRepository.findAllByIdCapacity(idCapacity);
  }
}
