package co.com.tecnologia.api.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import co.com.tecnologia.api.error.ErrorResponse;
import co.com.tecnologia.api.error.GlobalErrorWebFilter;
import co.com.tecnologia.api.handler.TechnologyHandler;
import co.com.tecnologia.api.model.request.TechnologyCreateRequest;
import co.com.tecnologia.api.model.response.TechnologyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
public class TechnologyRouterRest {

  private static final String V1 = "/api/v1";
  private static final String PATH = V1 + "/technology";
  private static final String VALIDATE_TECHNOLOGIES = PATH + "/validate";
  private static final String GET_TECHNOLOGIES_CAPACITY = V1 + "/capacity/{idCapacity}/technologies";
  private static final String DELETE_TECHNOLOGIES = V1 + "/capacity/{idCapacity}";

  private final TechnologyHandler technologyHandler;
  private final GlobalErrorWebFilter globalErrorWebFilter;

  @Bean
  @RouterOperations({@RouterOperation(method = RequestMethod.POST,
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
  ), @RouterOperation(method = RequestMethod.POST,
      path = VALIDATE_TECHNOLOGIES,
      beanClass = TechnologyHandler.class,
      beanMethod = "validateTechnologies",
      operation = @Operation(operationId = "validateTechnologies",
          summary = "Validar tecnologías",
          description = "Recibe lista de identificadores de las tecnologías",
          requestBody = @RequestBody(required = true,
              content = @Content(mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(type = "String", example = "[id1, id2, id3]"
                  )
                  )
              )
          ),
          responses = {@ApiResponse(responseCode = "204", description = "Tecnologías verificadas"
          ), @ApiResponse(responseCode = "404",
              description = "Tecnologia no encontrada",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class)
              )
          )}
      )
  ), @RouterOperation(method = RequestMethod.GET,
      path = GET_TECHNOLOGIES_CAPACITY,
      beanClass = TechnologyHandler.class,
      beanMethod = "getTechnologiesByIdCapacity",
      operation = @Operation(operationId = "getTechnologiesByIdCapacity",
          summary = "Obtener tecnologías por capacidad",
          description = "Recibe el identificador de la capacidad y devuelve la lista de tecnologías asociadas a esa capacidad",
          parameters = {@Parameter(name = "idCapacity",
              in = ParameterIn.PATH,
              required = true,
              description = "Identificador único de la capacidad",
              schema = @Schema(type = "string",
                  format = "uuid",
                  example = "a1b2c3d4-e5f6-7890-1234-567890abcdef"
              )
          )},
          responses = {@ApiResponse(responseCode = "200", description = "Tecnologías recuperadas"
          ), @ApiResponse(responseCode = "404",
              description = "Capacidad no encontrada",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class)
              )
          )}
      )
  ), @RouterOperation(method = RequestMethod.DELETE,
      path = DELETE_TECHNOLOGIES,
      beanClass = TechnologyHandler.class,
      beanMethod = "deleteTechnologiesByIdCapacity",
      operation = @Operation(operationId = "deleteTechnologiesByIdCapacity",
          summary = "Eliminar tecnologías por capacidad",
          description = "Recibe el identificador y elimina las tecnologías relacionadas a la capacidad.",
          parameters = {@Parameter(in = ParameterIn.PATH,
              description = "Identificador de la capacidad",
              schema = @Schema(type = "String")
          )},
          responses = {@ApiResponse(responseCode = "204",
              description = "Capacidad y tecnologías asociadas eliminadas correctamente"
          )}
      )
  )}
  )
  public RouterFunction<ServerResponse> technologyRouterFunction() {
    return route(POST(PATH), technologyHandler::createTechnology)
        .andRoute(POST(VALIDATE_TECHNOLOGIES), technologyHandler::validateTechnologies)
        .andRoute(GET(GET_TECHNOLOGIES_CAPACITY), technologyHandler::getTechnologiesByIdCapacity)
        .andRoute(DELETE(DELETE_TECHNOLOGIES), technologyHandler::deleteTechnologiesByIdCapacity)
        .filter(globalErrorWebFilter);
  }
}
