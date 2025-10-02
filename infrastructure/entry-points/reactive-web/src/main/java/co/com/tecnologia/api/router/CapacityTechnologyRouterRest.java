package co.com.tecnologia.api.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import co.com.tecnologia.api.error.GlobalErrorWebFilter;
import co.com.tecnologia.api.handler.CapacityTechnologyHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class CapacityTechnologyRouterRest {

  private static final String PATH = "/api/v1/capacity/{id}/technologies";
  private static final String PATH_COUNT = PATH + "/count";

  private final CapacityTechnologyHandler technologyHandler;
  private final GlobalErrorWebFilter globalErrorWebFilter;

  @Bean
  @RouterOperations({@RouterOperation(method = RequestMethod.POST,
      path = PATH,
      beanClass = CapacityTechnologyHandler.class,
      beanMethod = "createCapacityTechnology",
      operation = @Operation(operationId = "createCapacityTechnology",
          summary = "Asignar tecnologías a una capacidad",
          description = "Recibe datos de las tecnologías y de la capacidad.",
          requestBody = @RequestBody(required = true,
              content = @Content(mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(type = "String", example = "[id1, id2, id3]"
                  )
                  )
              )
          ),
          responses = {
              @ApiResponse(responseCode = "204", description = "Tecnologías asignadas correctamente"
              )}
      )
  ), @RouterOperation(method = RequestMethod.GET,
      path = PATH_COUNT,
      beanClass = CapacityTechnologyHandler.class,
      beanMethod = "countTechnologiesByIdCapacity",
      operation = @Operation(operationId = "countTechnologiesByIdCapacity",
          summary = "Contar tecnologías por capacidad",
          description = "Recibe el id de la capacidad y retorna la cantidad de tecnologías asignadas a dicha capacidad.",
          responses = {@ApiResponse(responseCode = "200",
              description = "Cantidad de tecnologías por capacidad",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = Long.class)
              )
          )}
      )
  )}
  )
  public RouterFunction<ServerResponse> capacityTechnologyRouterFunction() {
    return route(POST(PATH), technologyHandler::createCapacityTechnology)
        .andRoute(GET(PATH_COUNT), technologyHandler::countTechnologiesByIdCapacity)
        .filter(globalErrorWebFilter);
  }
}
