package com.openconsigplatform.credit.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CreditSimulation(
        UUID id,
        Cpf cpf,
        BigDecimal monthlyIncome,
        BigDecimal requestedAmount,
        int installments,
        BigDecimal monthlyInterestRate,
        BigDecimal monthlyInstallment,
        BigDecimal totalAmount,
        SimulationStatus status,
        Instant createdAt
) {

    public CreditSimulation {
        if (id == null || cpf == null || monthlyIncome == null || monthlyIncome.signum() <= 0
                || requestedAmount == null || requestedAmount.signum() <= 0
                || installments < 1 || installments > 84
                || monthlyInterestRate == null || monthlyInstallment == null
                || totalAmount == null || status == null || createdAt == null) {
            throw new InvalidCreditSimulationException("Credit simulation contains invalid data");
        }
    }
}
