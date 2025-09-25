package co.com.tecnologia.r2dbc.repository;

import co.com.tecnologia.r2dbc.entity.TechnologyEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TechnologyReactiveRepository extends
    ReactiveCrudRepository<TechnologyEntity, String>,
    ReactiveQueryByExampleExecutor<TechnologyEntity> {

  @Query("""
      SELECT t.*
      FROM tecnologia t
      INNER JOIN capacidad_tecnologias ct ON t.id_tecnologia = ct.id_tecnologia
      WHERE ct.id_capacidad = :idCapacity;
      """
  )
  Flux<TechnologyEntity> findTechnologiesByIdCapacity(String idCapacity);
}
