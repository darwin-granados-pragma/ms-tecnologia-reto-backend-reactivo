package co.com.tecnologia.api.handler;

import co.com.tecnologia.api.mapper.TechnologyRestMapper;
import co.com.tecnologia.api.model.request.TechnologyCreateRequest;
import co.com.tecnologia.model.technology.TechnologyCreate;
import co.com.tecnologia.usecase.technology.TechnologyUseCase;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class TechnologyHandler {

  private final TechnologyUseCase useCase;
  private final TechnologyRestMapper mapper;
  private final RequestValidator requestValidator;

  public Mono<ServerResponse> createTechnology(ServerRequest serverRequest) {
    log.info("Received request to create a technology at path={} method={}",
        serverRequest.path(),
        serverRequest.method()
    );
    return serverRequest
        .bodyToMono(TechnologyCreateRequest.class)
        .flatMap(request -> requestValidator
            .validate(request)
            .then(Mono.defer(() -> {
              TechnologyCreate technologyCreate = mapper.toTechnologyCreate(request);
              return useCase
                  .createTechnology(technologyCreate)
                  .map(mapper::toTechnologyResponse)
                  .flatMap(response -> ServerResponse
                      .status(HttpStatus.CREATED)
                      .contentType(MediaType.APPLICATION_JSON)
                      .bodyValue(response));
            })));
  }

  public Mono<ServerResponse> validateTechnologies(ServerRequest serverRequest) {
    log.info("Received request to validate technologies at path={} method={}",
        serverRequest.path(),
        serverRequest.method()
    );
    return serverRequest
        .bodyToMono(new ParameterizedTypeReference<Set<String>>() {
        })
        .flatMap(useCase::validateTechnologies)
        .then(ServerResponse
            .noContent()
            .build());
  }
}
