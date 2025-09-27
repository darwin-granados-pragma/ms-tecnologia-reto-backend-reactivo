package co.com.tecnologia.r2dbc.repository;

import co.com.tecnologia.r2dbc.entity.CapacityTechnologyEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CapacityTechnologyReactiveRepository extends
    ReactiveCrudRepository<CapacityTechnologyEntity, String>,
    ReactiveQueryByExampleExecutor<CapacityTechnologyEntity> {

  Flux<CapacityTechnologyEntity> findAllByIdCapacity(String idCapacity);
}
