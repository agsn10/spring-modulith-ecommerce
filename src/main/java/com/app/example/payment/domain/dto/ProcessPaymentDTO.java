package com.app.example.payment.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO de entrada e saída para o processamento de pagamento.
 * <p>
 * Representa os dados que trafegam entre a camada de apresentação e a aplicação
 * durante a operação de processamento de pagamento.
 * <p>
 * Alinhado com o contrato do caso de uso que manipula pagamentos.
 *
 * @author
 *     <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "DTO de entrada e saída para o processamento de pagamento")
public sealed interface ProcessPaymentDTO extends Serializable
        permits ProcessPaymentDTO.Request, ProcessPaymentDTO.Response {

    /**
     * Dados de entrada para processar um pagamento.
     *
     * @param orderId ID do pedido relacionado ao pagamento.
     * @param method  Método de pagamento utilizado.
     */
    @Schema(description = "Dados de entrada para processar um pagamento")
    record Request(
            @NotBlank(message = "{process.payment.request.orderId.notBlank}")
            @Schema(description = "ID do pedido", example = "order-1234")
            String orderId,

            @NotBlank(message = "{process.payment.request.method.notBlank}")
            @Schema(description = "Método de pagamento", example = "CREDIT_CARD")
            String method
    ) implements ProcessPaymentDTO {}

    /**
     * Dados de resposta após o processamento de um pagamento.
     *
     * @param id      Identificador único do pagamento gerado.
     * @param message Mensagem de confirmação ou erro.
     */
    @Schema(description = "Dados de resposta após o processamento de um pagamento")
    record Response(
            @Schema(description = "ID do pagamento", example = "payment-5678")
            String id,

            @Schema(description = "Mensagem de confirmação", example = "Pagamento processado com sucesso")
            String message
    ) implements ProcessPaymentDTO {}
}
