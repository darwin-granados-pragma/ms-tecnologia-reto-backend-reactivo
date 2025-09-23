package co.com.tecnologia.api.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import co.com.tecnologia.api.error.ErrorResponse;
import co.com.tecnologia.api.error.GlobalErrorWebFilter;
import co.com.tecnologia.api.handler.TechnologyHandler;
import co.com.tecnologia.api.model.request.TechnologyCreateRequest;
import co.com.tecnologia.api.model.response.TechnologyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class TechnologyRouterRest {

  private static final String PATH = "/api/v1/tecnologia";

  private final TechnologyHandler technologyHandler;
  private final GlobalErrorWebFilter globalErrorWebFilter;

  @Bean
  @RouterOperation(method = RequestMethod.POST,
      path = PATH,
      beanClass = TechnologyHandler.class,
      beanMethod = "createTechnology",
      operation = @Operation(operationId = "createTechnology",
          summary = "Crear tecnologia",
          description = "Recibe datos de la tecnologia y devuelve el objeto creado",
          requestBody = @RequestBody(required = true,
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = TechnologyCreateRequest.class)
              )
          ),
          responses = {@ApiResponse(responseCode = "200",
              description = "Tecnologia creada correctamente",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = TechnologyResponse.class)
              )
          ), @ApiResponse(responseCode = "400",
              description = "Parámetros inválidos o faltantes",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class)
              )
          ), @ApiResponse(responseCode = "409",
              description = "Tecnologia con el nombre ingresado ya se encuentra registrada",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class)
              )
          )}
      )
  )
  public RouterFunction<ServerResponse> technologyRouterFunction() {
    return route(POST(PATH), technologyHandler::createTechnology)
        .andRoute(POST(PATH.concat("/validate")), technologyHandler::validateTechnologies)
        .filter(globalErrorWebFilter);
  }
}
