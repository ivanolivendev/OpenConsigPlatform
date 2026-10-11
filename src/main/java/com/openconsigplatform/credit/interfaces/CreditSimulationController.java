package com.openconsigplatform.credit.interfaces;

import com.openconsigplatform.credit.application.SimulateCredit;
import com.openconsigplatform.credit.domain.CreditSimulation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/credit/simulations")
class CreditSimulationController {

    private final SimulateCredit simulateCredit;

    CreditSimulationController(SimulateCredit simulateCredit) {
        this.simulateCredit = simulateCredit;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreditSimulationResponse simulate(@RequestBody CreditSimulationRequest request) {
        CreditSimulation simulation = simulateCredit.execute(
                request.cpf(),
                request.monthlyIncome(),
                request.requestedAmount(),
                request.installments()
        );

        return CreditSimulationResponse.from(simulation);
    }

    record CreditSimulationRequest(
            String cpf,
            BigDecimal monthlyIncome,
            BigDecimal requestedAmount,
            int installments
    ) {
    }

    record CreditSimulationResponse(
            java.util.UUID simulationId,
            String cpf,
            BigDecimal requestedAmount,
            int installments,
            BigDecimal monthlyInterestRate,
            BigDecimal monthlyInstallment,
            BigDecimal totalAmount,
            String status,
            java.time.Instant createdAt
    ) {
        static CreditSimulationResponse from(CreditSimulation simulation) {
            return new CreditSimulationResponse(
                    simulation.id(),
                    simulation.cpf(),
                    simulation.requestedAmount(),
                    simulation.installments(),
                    simulation.monthlyInterestRate(),
                    simulation.monthlyInstallment(),
                    simulation.totalAmount(),
                    simulation.status().name(),
                    simulation.createdAt()
            );
        }
    }
}
