package com.openconsigplatform.credit.application;

import com.openconsigplatform.credit.domain.CreditSimulation;
import com.openconsigplatform.credit.domain.CreditSimulationService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class SimulateCreditTest {

    @Test
    void shouldDelegateToDomainAndPersistTheResult() {
        CapturingRepository repository = new CapturingRepository();
        SimulateCredit useCase = new SimulateCredit(new CreditSimulationService(), repository);

        CreditSimulation result = useCase.execute(new SimulateCreditCommand(
                "12345678900",
                new BigDecimal("5000.00"),
                new BigDecimal("10000.00"),
                24
        ));

        assertSame(result, repository.saved);
        assertEquals(new BigDecimal("516.22"), result.monthlyInstallment());
    }

    private static final class CapturingRepository implements CreditSimulationRepository {

        private CreditSimulation saved;

        @Override
        public CreditSimulation save(CreditSimulation simulation) {
            saved = simulation;
            return simulation;
        }

        @Override
        public Optional<CreditSimulation> findById(UUID id) {
            return Optional.empty();
        }
    }
}
