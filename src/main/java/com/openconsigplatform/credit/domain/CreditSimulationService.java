package com.openconsigplatform.credit.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.UUID;

public class CreditSimulationService {

    private static final BigDecimal MONTHLY_INTEREST_RATE = new BigDecimal("0.0179");
    private static final BigDecimal MAX_INSTALLMENT_PERCENTAGE = new BigDecimal("0.30");
    private static final int MIN_INSTALLMENTS = 1;
    private static final int MAX_INSTALLMENTS = 84;
    private static final MathContext CALCULATION_CONTEXT = MathContext.DECIMAL128;

    public CreditSimulation simulate(
            String cpf,
            BigDecimal monthlyIncome,
            BigDecimal requestedAmount,
            int installments
    ) {
        Cpf validatedCpf = new Cpf(cpf);
        validate(monthlyIncome, requestedAmount, installments);

        BigDecimal monthlyInstallment = calculateMonthlyInstallment(requestedAmount, installments);
        SimulationStatus status = monthlyInstallment.compareTo(
                monthlyIncome.multiply(MAX_INSTALLMENT_PERCENTAGE)
        ) <= 0 ? SimulationStatus.AVAILABLE : SimulationStatus.UNAVAILABLE;

        return new CreditSimulation(
                UUID.randomUUID(),
                validatedCpf,
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
        BigDecimal factor = BigDecimal.ONE
                .add(MONTHLY_INTEREST_RATE, CALCULATION_CONTEXT)
                .pow(numberOfInstallments, CALCULATION_CONTEXT);
        BigDecimal numerator = amount.multiply(MONTHLY_INTEREST_RATE, CALCULATION_CONTEXT)
                .multiply(factor, CALCULATION_CONTEXT);
        BigDecimal denominator = factor.subtract(BigDecimal.ONE, CALCULATION_CONTEXT);

        return numerator.divide(denominator, CALCULATION_CONTEXT)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private void validate(BigDecimal monthlyIncome, BigDecimal requestedAmount, int installments) {
        if (monthlyIncome == null || monthlyIncome.signum() <= 0) {
            throw new InvalidCreditSimulationException("Monthly income must be greater than zero");
        }
        if (requestedAmount == null || requestedAmount.signum() <= 0) {
            throw new InvalidCreditSimulationException("Requested amount must be greater than zero");
        }
        if (installments < MIN_INSTALLMENTS || installments > MAX_INSTALLMENTS) {
            throw new InvalidCreditSimulationException("Installments must be between 1 and 84");
        }
    }
}
