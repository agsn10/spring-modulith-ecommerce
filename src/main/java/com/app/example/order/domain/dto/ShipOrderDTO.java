package com.app.example.order.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO para a operação de expedição de pedido.
 *
 * Define os dados de requisição e resposta da operação de envio do pedido.
 * Alinha-se ao contrato do caso de uso {@code ShipOrderCommand}.
 *
 * @author Antonio
 */
@Schema(description = "DTO para expedição de pedido")
public sealed interface ShipOrderDTO extends Serializable
        permits ShipOrderDTO.Request, ShipOrderDTO.Response {

    /**
     * Dados da requisição para expedir um pedido.
     *
     * @param orderId Identificador do pedido a ser expedido.
     */
    @Schema(description = "Requisição para expedição de pedido")
    record Request(
            @NotBlank(message = "{ship.order.request.orderId.notBlank}")
            @Schema(description = "Identificador do pedido", example = "ORDER-98765")
            String orderId
    ) implements ShipOrderDTO {}

    /**
     * Resposta da operação de expedição do pedido.
     *
     * @param orderId Identificador do pedido expedido.
     * @param message Mensagem de confirmação da expedição.
     */
    @Schema(description = "Resposta da expedição de pedido")
    record Response(
            @Schema(description = "Identificador do pedido expedido", example = "ORDER-98765")
            String orderId,

            @Schema(description = "Mensagem de confirmação", example = "Pedido expedido com sucesso")
            String message
    ) implements ShipOrderDTO {}
}
