CREATE TABLE IF NOT EXISTS platform.credit_simulations (
    id UUID PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL,
    monthly_income NUMERIC(15, 2) NOT NULL,
    requested_amount NUMERIC(15, 2) NOT NULL,
    installments INTEGER NOT NULL,
    monthly_interest_rate NUMERIC(8, 4) NOT NULL,
    monthly_installment NUMERIC(15, 2) NOT NULL,
    total_amount NUMERIC(15, 2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_credit_simulations_cpf
    ON platform.credit_simulations (cpf);
