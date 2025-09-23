package co.com.tecnologia.usecase.technology;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import co.com.tecnologia.model.exception.ObjectNotFoundException;
import co.com.tecnologia.model.gateways.TechnologyRepository;
import co.com.tecnologia.model.technology.Technology;
import co.com.tecnologia.model.technology.TechnologyCreate;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class TechnologyUseCaseTest {

  @Mock
  private TechnologyRepository repository;

  @InjectMocks
  private TechnologyUseCase useCase;

  private TechnologyCreate technologyCreate;
  private Technology technology;

  @BeforeEach
  void setUp() {
    technologyCreate = new TechnologyCreate("test name", "test description");
    technology = Technology
        .builder()
        .id("test id")
        .name(technologyCreate.name())
        .description(technologyCreate.description())
        .build();
  }

  @Test
  void shouldReturnTechnologySaved() {
    // Arrange
    when(repository.save(any(Technology.class))).thenReturn(Mono.just(technology));

    // Act
    var result = useCase.createTechnology(technologyCreate);

    // Assert
    StepVerifier
        .create(result)
        .expectNextMatches(created -> created
            .getName()
            .equals(technology.getName()) && created
            .getDescription()
            .equals(technology.getDescription()))
        .verifyComplete();
  }

  @Test
  void validateTechnologies_ShouldCompleteWhenAllIdsExist() {
    // Arrange
    Set<String> existingIds = Set.of("id-1", "id-2", "id-3");
    when(repository.existsById(anyString())).thenReturn(Mono.just(true));

    // Act
    Mono<Void> result = useCase.validateTechnologies(existingIds);

    // Assert
    StepVerifier
        .create(result)
        .verifyComplete();
  }

  @Test
  void validateTechnologies_ShouldReturnErrorWhenOneIdDoesNotExist() {
    // Arrange
    String invalidId = "invalid-id";
    String validId1 = "id-1";
    String validId3 = "id-3";
    Set<String> idsToValidate = Set.of(validId1, invalidId, validId3);

    lenient()
        .when(repository.existsById(validId1))
        .thenReturn(Mono.just(true));
    lenient()
        .when(repository.existsById(validId3))
        .thenReturn(Mono.just(true));
    when(repository.existsById(invalidId)).thenReturn(Mono.just(false));

    // Act
    Mono<Void> result = useCase.validateTechnologies(idsToValidate);

    // Assert
    StepVerifier
        .create(result)
        .expectError(ObjectNotFoundException.class)
        .verify();
  }
}