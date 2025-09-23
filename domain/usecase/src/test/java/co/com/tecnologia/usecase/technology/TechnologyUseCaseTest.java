package co.com.tecnologia.usecase.technology;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import co.com.tecnologia.model.technology.Technology;
import co.com.tecnologia.model.technology.TechnologyCreate;
import co.com.tecnologia.model.technology.gateways.TechnologyRepository;
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
            .equals(technology.getDescription())).verifyComplete();
  }
}