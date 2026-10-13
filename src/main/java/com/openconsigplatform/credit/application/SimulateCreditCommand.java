package com.openconsigplatform.credit.application;

import java.math.BigDecimal;

public record SimulateCreditCommand(
        String cpf,
        BigDecimal monthlyIncome,
        BigDecimal requestedAmount,
        int installments
) {
}
