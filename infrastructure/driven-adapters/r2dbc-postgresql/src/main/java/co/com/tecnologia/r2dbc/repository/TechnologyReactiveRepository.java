package co.com.tecnologia.r2dbc.repository;

import co.com.tecnologia.r2dbc.entity.TechnologyEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TechnologyReactiveRepository extends
    ReactiveCrudRepository<TechnologyEntity, String>,
    ReactiveQueryByExampleExecutor<TechnologyEntity> {

}
