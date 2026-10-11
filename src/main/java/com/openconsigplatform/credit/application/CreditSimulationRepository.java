package com.openconsigplatform.credit.application;

import com.openconsigplatform.credit.domain.CreditSimulation;

import java.util.Optional;
import java.util.UUID;

public interface CreditSimulationRepository {

    CreditSimulation save(CreditSimulation simulation);

    Optional<CreditSimulation> findById(UUID id);
}
