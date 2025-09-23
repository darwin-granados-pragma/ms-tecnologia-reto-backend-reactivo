package co.com.tecnologia.usecase.technology.capacity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import co.com.tecnologia.model.gateways.CapacityTechnologyRepository;
import co.com.tecnologia.model.gateways.TransactionalGateway;
import co.com.tecnologia.model.technology.capacity.CapacityTechnology;
import co.com.tecnologia.model.technology.capacity.CapacityTechnologyCreate;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CapacityTechnologyUseCaseTest {

  CapacityTechnologyCreate capacityTechnologyCreate = new CapacityTechnologyCreate("cap-01",
      Set.of("tech-java", "tech-react", "tech-postgres")
  );

  @Mock
  private CapacityTechnologyRepository repository;
  @Mock
  private TransactionalGateway transactionalGateway;

  @InjectMocks
  private CapacityTechnologyUseCase capacityTechnologyUseCase;

  @Test
  void shouldSaveAllAssociations() {
    // Arrange
    when(repository.save(any(CapacityTechnology.class))).thenReturn(Mono.just(new CapacityTechnology()));
    when(transactionalGateway.execute(ArgumentMatchers.<Mono<?>>any())).thenAnswer(invocation -> invocation.getArgument(
        0));

    // Act
    Mono<Void> result = capacityTechnologyUseCase.assignTechnologiesToCapacity(
        capacityTechnologyCreate);

    // Assert
    StepVerifier
        .create(result)
        .verifyComplete();
    verify(repository, times(3)).save(any(CapacityTechnology.class));
    verify(transactionalGateway, times(1)).execute(any());
  }
}