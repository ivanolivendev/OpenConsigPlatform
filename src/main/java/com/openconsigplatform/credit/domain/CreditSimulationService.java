package com.openconsigplatform.credit.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.UUID;

public class CreditSimulationService {

    private static final BigDecimal MONTHLY_INTEREST_RATE = new BigDecimal("0.0179");
    private static final BigDecimal MAX_INSTALLMENT_PERCENTAGE = new BigDecimal("0.30");
    private static final int MIN_INSTALLMENTS = 1;
    private static final int MAX_INSTALLMENTS = 84;

    public CreditSimulation simulate(
            String cpf,
            BigDecimal monthlyIncome,
            BigDecimal requestedAmount,
            int installments
    ) {
        validate(cpf, monthlyIncome, requestedAmount, installments);

        BigDecimal monthlyInstallment = calculateMonthlyInstallment(requestedAmount, installments);
        SimulationStatus status = monthlyInstallment.compareTo(
                monthlyIncome.multiply(MAX_INSTALLMENT_PERCENTAGE)
        ) <= 0 ? SimulationStatus.AVAILABLE : SimulationStatus.UNAVAILABLE;

        return new CreditSimulation(
                UUID.randomUUID(),
                cpf,
                monthlyIncome.setScale(2, RoundingMode.HALF_UP),
                requestedAmount.setScale(2, RoundingMode.HALF_UP),
                installments,
                MONTHLY_INTEREST_RATE.multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP),
                monthlyInstallment,
                monthlyInstallment.multiply(BigDecimal.valueOf(installments)).setScale(2, RoundingMode.HALF_UP),
                status,
                Instant.now()
        );
    }

    private BigDecimal calculateMonthlyInstallment(BigDecimal amount, int numberOfInstallments) {
        double principal = amount.doubleValue();
        double rate = MONTHLY_INTEREST_RATE.doubleValue();
        double factor = Math.pow(1 + rate, numberOfInstallments);
        double installment = principal * rate * factor / (factor - 1);

        return BigDecimal.valueOf(installment).setScale(2, RoundingMode.HALF_UP);
    }

    private void validate(String cpf, BigDecimal monthlyIncome, BigDecimal requestedAmount, int installments) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF must contain exactly 11 digits");
        }
        if (monthlyIncome == null || monthlyIncome.signum() <= 0) {
            throw new IllegalArgumentException("Monthly income must be greater than zero");
        }
        if (requestedAmount == null || requestedAmount.signum() <= 0) {
            throw new IllegalArgumentException("Requested amount must be greater than zero");
        }
        if (installments < MIN_INSTALLMENTS || installments > MAX_INSTALLMENTS) {
            throw new IllegalArgumentException("Installments must be between 1 and 84");
        }
    }
}
