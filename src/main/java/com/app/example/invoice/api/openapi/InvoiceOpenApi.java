package com.app.example.invoice.api.openapi;

import com.app.example.invoice.domain.dto.GenerateInvoiceDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@Tag(
        name = "Fatura API",
        description = "Operações relacionadas à geração de faturas"
)
public interface InvoiceOpenApi {

    @Operation(
            summary = "Gerar fatura",
            description = "Gera uma fatura com base no ID do pedido fornecido",
            operationId = "generate",
            tags = {"Fatura API"},
            parameters = {
                    @Parameter(
                            name = "orderId",
                            description = "ID do pedido para o qual a fatura será gerada",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fatura gerada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenerateInvoiceDTO.Response.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pedido não encontrado"
                    )
            }
    )
    public Mono<GenerateInvoiceDTO.Response> generate(String orderId);
}
