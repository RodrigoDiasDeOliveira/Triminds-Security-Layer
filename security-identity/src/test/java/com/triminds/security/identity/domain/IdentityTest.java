package com.triminds.security.identity.domain;

import com.triminds.security.identity.domain.events.*;
import com.triminds.security.identity.domain.model.*;
import org.junit.jupiter.api.*; import org.junit.jupiter.api.Nested;
import java.time.Instant; import java.time.temporal.ChronoUnit; import java.util.List;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Identity — testes de domínio")
class IdentityTest {
    static final String ORG = "550e8400-e29b-41d4-a716-446655440000";
    Identity identity;

    @BeforeEach void setup() {
        identity = Identity.create(OrganizationId.of(ORG), "joao.silva", "joao@triminds.com", "João", IdentityType.LOCAL_USER);
    }

    @Nested @DisplayName("Criação") class Creation {
        @Test void createSuccess() {
            assertThat(identity.getStatus()).isEqualTo(IdentityStatus.PENDING_ACTIVATION);
            assertThat(identity.pullDomainEvents()).hasSize(1).first().isInstanceOf(IdentityCreatedEvent.class);
        }
        @Test void invalidEmail() {
            assertThatThrownBy(() -> Identity.create(OrganizationId.of(ORG), "u", "invalido", null, IdentityType.LOCAL_USER))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested @DisplayName("Ciclo de vida") class Lifecycle {
        @Test void activate() {
            identity.activate();
            assertThat(identity.getStatus()).isEqualTo(IdentityStatus.ACTIVE);
            assertThat(identity.pullDomainEvents()).anyMatch(e -> e instanceof IdentityActivatedEvent);
        }
        @Test void lockAndUnlock() {
            identity.activate(); identity.pullDomainEvents();
            identity.lock("teste", Instant.now().plus(30, ChronoUnit.MINUTES));
            assertThat(identity.isLocked()).isTrue();
            identity.unlock();
            assertThat(identity.getStatus()).isEqualTo(IdentityStatus.ACTIVE);
            assertThat(identity.getFailedLoginAttempts()).isZero();
        }
        @Test void autoLock() {
            identity.activate();
            Instant until = Instant.now().plus(30, ChronoUnit.MINUTES);
            for (int i = 0; i < 5; i++) identity.recordFailedLogin(5, until);
            assertThat(identity.getStatus()).isEqualTo(IdentityStatus.LOCKED);
        }
    }

    @Nested @DisplayName("Eventos") class Events {
        @Test void pullClearsEvents() {
            List<IdentityDomainEvent> first = identity.pullDomainEvents();
            assertThat(first).hasSize(1);
            assertThat(identity.pullDomainEvents()).isEmpty();
        }
    }
}
