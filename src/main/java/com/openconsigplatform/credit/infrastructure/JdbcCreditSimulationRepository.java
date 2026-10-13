package com.openconsigplatform.credit.infrastructure;

import com.openconsigplatform.credit.application.CreditSimulationRepository;
import com.openconsigplatform.credit.domain.CreditSimulation;
import com.openconsigplatform.credit.domain.Cpf;
import com.openconsigplatform.credit.domain.SimulationStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.sql.Timestamp;

@Repository
class JdbcCreditSimulationRepository implements CreditSimulationRepository {

    private final JdbcTemplate jdbcTemplate;

    JdbcCreditSimulationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CreditSimulation save(CreditSimulation simulation) {
        jdbcTemplate.update("""
                INSERT INTO platform.credit_simulations (
                    id, cpf, monthly_income, requested_amount, installments,
                    monthly_interest_rate, monthly_installment, total_amount,
                    status, created_at
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """,
                simulation.id(),
                simulation.cpf().value(),
                simulation.monthlyIncome(),
                simulation.requestedAmount(),
                simulation.installments(),
                simulation.monthlyInterestRate(),
                simulation.monthlyInstallment(),
                simulation.totalAmount(),
                simulation.status().name(),
                Timestamp.from(simulation.createdAt())
        );

        return simulation;
    }

    @Override
    public Optional<CreditSimulation> findById(UUID id) {
        return jdbcTemplate.query("""
                SELECT id, cpf, monthly_income, requested_amount, installments,
                       monthly_interest_rate, monthly_installment, total_amount,
                       status, created_at
                FROM platform.credit_simulations
                WHERE id = ?
                """, (resultSet, rowNum) -> new CreditSimulation(
                resultSet.getObject("id", UUID.class),
                new Cpf(resultSet.getString("cpf")),
                resultSet.getBigDecimal("monthly_income"),
                resultSet.getBigDecimal("requested_amount"),
                resultSet.getInt("installments"),
                resultSet.getBigDecimal("monthly_interest_rate"),
                resultSet.getBigDecimal("monthly_installment"),
                resultSet.getBigDecimal("total_amount"),
                SimulationStatus.valueOf(resultSet.getString("status")),
                resultSet.getTimestamp("created_at").toInstant()
        ), id).stream().findFirst();
    }
}
