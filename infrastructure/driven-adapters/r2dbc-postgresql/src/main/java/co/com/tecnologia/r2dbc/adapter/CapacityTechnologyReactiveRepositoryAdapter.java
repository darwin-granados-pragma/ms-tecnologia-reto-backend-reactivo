package co.com.tecnologia.r2dbc.adapter;

import co.com.tecnologia.model.gateways.CapacityTechnologyRepository;
import co.com.tecnologia.model.technology.capacity.CapacityTechnology;
import co.com.tecnologia.r2dbc.entity.CapacityTechnologyEntity;
import co.com.tecnologia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.tecnologia.r2dbc.repository.CapacityTechnologyReactiveRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class CapacityTechnologyReactiveRepositoryAdapter extends
    ReactiveAdapterOperations<CapacityTechnology, CapacityTechnologyEntity, String, CapacityTechnologyReactiveRepository> implements
    CapacityTechnologyRepository {

  public CapacityTechnologyReactiveRepositoryAdapter(
      CapacityTechnologyReactiveRepository repository, ObjectMapper mapper) {
    super(repository, mapper, d -> mapper.map(d, CapacityTechnology.class));
  }

  @Override
  public Mono<CapacityTechnology> save(CapacityTechnology data) {
    log.info("Saving Capacity-Technology");
    return super
        .save(data)
        .doOnSuccess(capacityTechnology -> log.debug("Capacity-Technology saved: {}",
            capacityTechnology
        ));
  }

  @Override
  public Flux<CapacityTechnology> findAllByIdCapacity(String idCapacity) {
    return super.repository
        .findAllByIdCapacity(idCapacity)
        .map(this::toEntity);
  }

  @Override
  public Mono<Void> deleteAll(List<CapacityTechnology> capacityTechnologyList) {
    List<CapacityTechnologyEntity> list = capacityTechnologyList
        .stream()
        .map(this::toData)
        .toList();
    return super.repository.deleteAll(list);
  }
}
