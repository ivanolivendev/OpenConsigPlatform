package com.openconsigplatform.credit.infrastructure;

import com.openconsigplatform.credit.domain.CreditSimulationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CreditInfrastructureConfiguration {

    @Bean
    CreditSimulationService creditSimulationService() {
        return new CreditSimulationService();
    }
}
