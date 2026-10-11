package com.openconsigplatform.credit.application;

import com.openconsigplatform.credit.domain.CreditSimulation;
import com.openconsigplatform.credit.domain.CreditSimulationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SimulateCredit {

    private final CreditSimulationService simulationService;
    private final CreditSimulationRepository simulationRepository;

    public SimulateCredit(
            CreditSimulationService simulationService,
            CreditSimulationRepository simulationRepository
    ) {
        this.simulationService = simulationService;
        this.simulationRepository = simulationRepository;
    }

    public CreditSimulation execute(
            String cpf,
            BigDecimal monthlyIncome,
            BigDecimal requestedAmount,
            int installments
    ) {
        CreditSimulation simulation = simulationService.simulate(
                cpf,
                monthlyIncome,
                requestedAmount,
                installments
        );

        return simulationRepository.save(simulation);
    }
}
