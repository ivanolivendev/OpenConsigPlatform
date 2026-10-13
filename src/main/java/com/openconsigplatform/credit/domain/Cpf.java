package com.openconsigplatform.credit.domain;

public record Cpf(String value) {

    public Cpf {
        if (value == null || !value.matches("\\d{11}")) {
            throw new InvalidCreditSimulationException("CPF must contain exactly 11 digits");
        }
    }
}
