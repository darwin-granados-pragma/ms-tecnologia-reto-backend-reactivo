package co.com.tecnologia.api.handler;

import co.com.tecnologia.api.mapper.CapacityTechnologyRestMapper;
import co.com.tecnologia.usecase.technology.capacity.CapacityTechnologyUseCase;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CapacityTechnologyHandler {

  private final CapacityTechnologyUseCase useCase;
  private final CapacityTechnologyRestMapper mapper;

  public Mono<ServerResponse> createCapacityTechnology(ServerRequest serverRequest) {
    log.info("Received request to save technologies to a capacity at path={} method={}",
        serverRequest.path(),
        serverRequest.method()
    );
    String idCapacity = serverRequest.pathVariable("id");
    return serverRequest
        .bodyToMono(new ParameterizedTypeReference<Set<String>>() {
        })
        .flatMap(idTechnologies -> useCase.assignTechnologiesToCapacity(mapper
            .toCapacityTechnologyCreate(idCapacity, idTechnologies
        )))
        .then(ServerResponse
            .noContent()
            .build());
  }

}
