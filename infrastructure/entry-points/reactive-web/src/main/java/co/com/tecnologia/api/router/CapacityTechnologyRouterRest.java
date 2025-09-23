package co.com.tecnologia.api.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import co.com.tecnologia.api.error.GlobalErrorWebFilter;
import co.com.tecnologia.api.handler.CapacityTechnologyHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class CapacityTechnologyRouterRest {

  private final CapacityTechnologyHandler technologyHandler;
  private final GlobalErrorWebFilter globalErrorWebFilter;

  @Bean
  public RouterFunction<ServerResponse> capacityTechnologyRouterFunction() {
    return route(POST("/api/v1/capacities/{id}/technologies"),
        technologyHandler::createCapacityTechnology
    ).filter(globalErrorWebFilter);
  }
}
