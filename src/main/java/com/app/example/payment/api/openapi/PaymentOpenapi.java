package com.app.example.payment.api.openapi;

import com.app.example.payment.domain.dto.ProcessPaymentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@Tag(
        name = "Pagamento API",
        description = "Operações relacionadas ao processamento de pagamentos"
)
public interface PaymentOpenapi {

    @Operation(
            summary = "Processar pagamento",
            description = "Processa o pagamento de um pedido com o método de pagamento especificado",
            operationId = "processPayment",
            tags = {"Pagamento API"},
            parameters = {
                    @Parameter(
                            name = "orderId",
                            description = "ID do pedido a ser pago",
                            required = true
                    ),
                    @Parameter(
                            name = "method",
                            description = "Método de pagamento (ex: cartão, pix, boleto)",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pagamento processado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProcessPaymentDTO.Response.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados inválidos ou método de pagamento não suportado"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pedido não encontrado"
                    )
            }
    )
    Mono<ProcessPaymentDTO.Response> process(String orderId, String method);
}