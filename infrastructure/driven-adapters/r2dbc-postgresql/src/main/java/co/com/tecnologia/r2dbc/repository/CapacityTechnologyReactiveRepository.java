package co.com.tecnologia.r2dbc.repository;

import co.com.tecnologia.r2dbc.entity.CapacityTechnologyEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CapacityTechnologyReactiveRepository extends
    ReactiveCrudRepository<CapacityTechnologyEntity, String>,
    ReactiveQueryByExampleExecutor<CapacityTechnologyEntity> {

}
