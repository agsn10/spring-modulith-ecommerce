package com.app.example.order.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO de requisição e resposta para marcação de pedido como pago.
 * <p>
 * Representa os dados trafegados via API durante a operação de confirmação de pagamento do pedido.
 *
 * Alinhado ao contrato do caso de uso {@code MarkOrderAsPaidCommand}.
 *
 * @author Antonio Neto
 */
@Schema(description = "DTO de requisição e resposta para marcar pedido como pago")
public sealed interface MarkOrderAsPaidDTO extends Serializable
        permits MarkOrderAsPaidDTO.Request, MarkOrderAsPaidDTO.Response {

    /**
     * Dados de requisição para marcar um pedido como pago.
     *
     * @param orderId Identificador do pedido que será marcado como pago.
     */
    @Schema(description = "Dados de requisição para marcar pedido como pago")
    record Request(
            @NotBlank(message = "{mark.order.paid.request.orderId.notBlank}")
            @Schema(description = "Identificador do pedido", example = "ORDER-67890")
            String orderId
    ) implements MarkOrderAsPaidDTO {}

    /**
     * Resposta da operação de marcação de pedido como pago.
     *
     * @param orderId Identificador do pedido marcado como pago.
     * @param message Mensagem de confirmação.
     */
    @Schema(description = "Resposta da operação de marcação de pedido como pago")
    record Response(
            @Schema(description = "Identificador do pedido pago", example = "ORDER-67890")
            String orderId,

            @Schema(description = "Mensagem de confirmação", example = "Pagamento confirmado com sucesso")
            String message
    ) implements MarkOrderAsPaidDTO {}
}

