package com.app.example.order.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO de entrada e saída para o cancelamento de pedido.
 * <p>
 * Representa os dados que trafegam na API (camada de apresentação) durante a operação de cancelamento de pedido.
 * Alinhado com o contrato do caso de uso {@code CancelOrderCommand}.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "DTO de entrada e saída para cancelamento de pedido")
public sealed interface CancelOrderDTO extends Serializable
        permits CancelOrderDTO.Request, CancelOrderDTO.Response {

    /**
     * Dados de entrada para cancelamento de pedido.
     *
     * @param orderId Identificador do pedido a ser cancelado.
     */
    @Schema(description = "Dados de entrada para cancelamento de pedido")
    record Request(
            @NotBlank(message = "{cancel.order.request.orderId.notBlank}")
            @Schema(description = "Identificador do pedido", example = "ORDER-12345")
            String orderId
    ) implements CancelOrderDTO {}

    /**
     * Resultado da operação de cancelamento de pedido.
     *
     * @param orderId Identificador do pedido cancelado.
     * @param message Mensagem de confirmação do cancelamento.
     */
    @Schema(description = "Resultado da operação de cancelamento de pedido")
    record Response(
            @Schema(description = "Identificador do pedido cancelado", example = "ORDER-12345")
            String orderId,

            @Schema(description = "Mensagem de confirmação", example = "Pedido cancelado com sucesso")
            String message
    ) implements CancelOrderDTO {}
}

