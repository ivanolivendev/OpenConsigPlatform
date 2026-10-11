package com.openconsigplatform.credit.interfaces;

import com.openconsigplatform.credit.application.SimulateCredit;
import com.openconsigplatform.credit.domain.CreditSimulation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/credit/simulations")
@Tag(name = "Credit Simulation", description = "Simulações internas de crédito consignado")
class CreditSimulationController {

    private final SimulateCredit simulateCredit;

    CreditSimulationController(SimulateCredit simulateCredit) {
        this.simulateCredit = simulateCredit;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Simular crédito consignado",
            description = "Calcula a parcela e o valor total de uma simulação interna. "
                    + "A simulação é marcada como AVAILABLE quando a parcela não ultrapassa 30% da renda mensal. "
                    + "O CPF deve conter exatamente 11 dígitos, a renda e o valor solicitado devem ser positivos, "
                    + "e o prazo deve estar entre 1 e 84 parcelas."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Simulação criada e persistida",
                    content = @Content(
                            schema = @Schema(implementation = CreditSimulationResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Available",
                                            summary = "Parcela dentro do limite de renda",
                                            value = "{\"simulationId\":\"63717ce1-ae2e-4323-8207-a78d251ee0fc\","
                                                    + "\"cpf\":\"12345678900\",\"requestedAmount\":10000.00,"
                                                    + "\"installments\":24,\"monthlyInterestRate\":1.79,"
                                                    + "\"monthlyInstallment\":516.22,\"totalAmount\":12389.28,"
                                                    + "\"status\":\"AVAILABLE\",\"createdAt\":\"2026-10-11T17:44:21Z\"}"
                                    ),
                                    @ExampleObject(
                                            name = "Unavailable",
                                            summary = "Parcela acima de 30% da renda",
                                            value = "{\"simulationId\":\"63717ce1-ae2e-4323-8207-a78d251ee0fc\","
                                                    + "\"cpf\":\"12345678900\",\"requestedAmount\":50000.00,"
                                                    + "\"installments\":12,\"monthlyInterestRate\":1.79,"
                                                    + "\"monthlyInstallment\":4720.00,\"totalAmount\":56640.00,"
                                                    + "\"status\":\"UNAVAILABLE\",\"createdAt\":\"2026-10-11T17:44:21Z\"}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos: CPF diferente de 11 dígitos, renda ou valor não positivos, "
                            + "ou prazo fora do intervalo de 1 a 84 parcelas",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            name = "InvalidCpf",
                                            summary = "CPF inválido",
                                            value = "CPF must contain exactly 11 digits"
                                    ),
                                    @ExampleObject(
                                            name = "InvalidIncome",
                                            summary = "Renda inválida",
                                            value = "Monthly income must be greater than zero"
                                    ),
                                    @ExampleObject(
                                            name = "InvalidAmount",
                                            summary = "Valor solicitado inválido",
                                            value = "Requested amount must be greater than zero"
                                    ),
                                    @ExampleObject(
                                            name = "InvalidInstallments",
                                            summary = "Prazo inválido",
                                            value = "Installments must be between 1 and 84"
                                    )
                            }
                    )
            )
    })
    CreditSimulationResponse simulate(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Dados de entrada da simulação",
                    content = @Content(
                            schema = @Schema(implementation = CreditSimulationRequest.class),
                            examples = @ExampleObject(
                                    name = "Default simulation",
                                    summary = "Exemplo de simulação",
                                    value = "{\"cpf\":\"12345678900\",\"monthlyIncome\":5000.00,"
                                            + "\"requestedAmount\":10000.00,\"installments\":24}"
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody CreditSimulationRequest request
    ) {
        CreditSimulation simulation = simulateCredit.execute(
                request.cpf(),
                request.monthlyIncome(),
                request.requestedAmount(),
                request.installments()
        );

        return CreditSimulationResponse.from(simulation);
    }

    record CreditSimulationRequest(
            @Schema(description = "CPF com exatamente 11 dígitos", example = "12345678900")
            String cpf,
            @Schema(description = "Renda mensal do cliente, maior que zero", example = "5000.00")
            BigDecimal monthlyIncome,
            @Schema(description = "Valor solicitado, maior que zero", example = "10000.00")
            BigDecimal requestedAmount,
            @Schema(description = "Quantidade de parcelas entre 1 e 84", example = "24", minimum = "1", maximum = "84")
            int installments
    ) {
    }

    record CreditSimulationResponse(
            @Schema(description = "Identificador único da simulação")
            java.util.UUID simulationId,
            @Schema(description = "CPF informado na requisição", example = "12345678900")
            String cpf,
            @Schema(description = "Valor solicitado", example = "10000.00")
            BigDecimal requestedAmount,
            @Schema(description = "Quantidade de parcelas", example = "24")
            int installments,
            @Schema(description = "Taxa de juros mensal em percentual", example = "1.79")
            BigDecimal monthlyInterestRate,
            @Schema(description = "Valor calculado da parcela mensal", example = "516.22")
            BigDecimal monthlyInstallment,
            @Schema(description = "Valor total da operação", example = "12389.28")
            BigDecimal totalAmount,
            @Schema(description = "AVAILABLE quando a parcela cabe em 30% da renda; caso contrário UNAVAILABLE",
                    allowableValues = {"AVAILABLE", "UNAVAILABLE"})
            String status,
            @Schema(description = "Data e hora de criação da simulação")
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
