package com.openconsigplatform.credit.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreditSimulationServiceTest {

    private final CreditSimulationService service = new CreditSimulationService();

    @Test
    void shouldCreateAvailableSimulationUsingFinancialPrecision() {
        CreditSimulation simulation = service.simulate(
                "12345678900",
                new BigDecimal("5000.00"),
                new BigDecimal("10000.00"),
                24
        );

        assertEquals(new BigDecimal("516.22"), simulation.monthlyInstallment());
        assertEquals(new BigDecimal("12389.28"), simulation.totalAmount());
        assertEquals(SimulationStatus.AVAILABLE, simulation.status());
        assertEquals(new Cpf("12345678900"), simulation.cpf());
    }

    @Test
    void shouldMarkSimulationUnavailableWhenInstallmentExceedsIncomeLimit() {
        CreditSimulation simulation = service.simulate(
                "12345678900",
                new BigDecimal("5000.00"),
                new BigDecimal("50000.00"),
                12
        );

        assertEquals(SimulationStatus.UNAVAILABLE, simulation.status());
    }

    @Test
    void shouldRejectInvalidCpf() {
        assertThrows(InvalidCreditSimulationException.class, () -> service.simulate(
                "123",
                new BigDecimal("5000.00"),
                new BigDecimal("10000.00"),
                24
        ));
    }

    @Test
    void shouldRejectInvalidFinancialInputs() {
        assertThrows(InvalidCreditSimulationException.class, () -> service.simulate(
                "12345678900",
                BigDecimal.ZERO,
                new BigDecimal("10000.00"),
                24
        ));

        assertThrows(InvalidCreditSimulationException.class, () -> service.simulate(
                "12345678900",
                new BigDecimal("5000.00"),
                new BigDecimal("10000.00"),
                85
        ));
    }
}
