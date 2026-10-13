package com.openconsigplatform.credit.application;

import com.openconsigplatform.credit.domain.CreditSimulation;
import com.openconsigplatform.credit.domain.CreditSimulationService;
import org.springframework.stereotype.Service;

@Service
public class SimulateCredit implements SimulateCreditUseCase {

    private final CreditSimulationService simulationService;
    private final CreditSimulationRepository simulationRepository;

    public SimulateCredit(
            CreditSimulationService simulationService,
            CreditSimulationRepository simulationRepository
    ) {
        this.simulationService = simulationService;
        this.simulationRepository = simulationRepository;
    }

    @Override
    public CreditSimulation execute(SimulateCreditCommand command) {
        CreditSimulation simulation = simulationService.simulate(
                command.cpf(),
                command.monthlyIncome(),
                command.requestedAmount(),
                command.installments()
        );

        return simulationRepository.save(simulation);
    }
}
