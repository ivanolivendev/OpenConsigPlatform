package com.openconsigplatform.credit.application;

import com.openconsigplatform.credit.domain.CreditSimulation;

public interface SimulateCreditUseCase {

    CreditSimulation execute(SimulateCreditCommand command);
}
