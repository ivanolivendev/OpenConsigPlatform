package com.openconsigplatform.credit.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CreditSimulation(
        UUID id,
        String cpf,
        BigDecimal monthlyIncome,
        BigDecimal requestedAmount,
        int installments,
        BigDecimal monthlyInterestRate,
        BigDecimal monthlyInstallment,
        BigDecimal totalAmount,
        SimulationStatus status,
        Instant createdAt
) {
}
