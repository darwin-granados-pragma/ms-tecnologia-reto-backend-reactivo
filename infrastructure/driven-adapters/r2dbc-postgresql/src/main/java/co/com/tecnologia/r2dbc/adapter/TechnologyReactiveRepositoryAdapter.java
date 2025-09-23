package co.com.tecnologia.r2dbc.adapter;

import co.com.tecnologia.model.error.ErrorCode;
import co.com.tecnologia.model.exception.ConstraintException;
import co.com.tecnologia.model.gateways.TechnologyRepository;
import co.com.tecnologia.model.technology.Technology;
import co.com.tecnologia.r2dbc.entity.TechnologyEntity;
import co.com.tecnologia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.tecnologia.r2dbc.repository.TechnologyReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class TechnologyReactiveRepositoryAdapter extends
    ReactiveAdapterOperations<Technology, TechnologyEntity, String, TechnologyReactiveRepository> implements
    TechnologyRepository {

  private final TransactionalOperator operator;

  public TechnologyReactiveRepositoryAdapter(TechnologyReactiveRepository repository,
      ObjectMapper mapper, TransactionalOperator operator) {
    super(repository, mapper, d -> mapper.map(d, Technology.class));
    this.operator = operator;
  }

  @Override
  public Mono<Technology> save(Technology technology) {
    log.info("Saving technology with name: {}", technology.getName());
    return super
        .save(technology)
        .onErrorMap(DataIntegrityViolationException.class, e -> {
              log.error("Data integrity violation: {}", e.getMessage());
              String message = e.getMessage();
              if (message.contains("nombre_unique_constraint")) {
                return new ConstraintException(ErrorCode.TECHNOLOGY_NAME_ALREADY_EXISTS);
              }
              return new ConstraintException(ErrorCode.CONSTRAINT_VIOLATION);
            }
        )
        .as(operator::transactional)
        .doOnSuccess(technologySaved -> log.debug("Technology saved: {}", technologySaved));
  }

  @Override
  public Mono<Boolean> existsById(String id) {
    log.info("Validating existence of the technology by id: {}", id);
    return super.repository.existsById(id);
  }
}
