package com.app.example.order.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Comando para busca de pedido por ID.
 * <p>
 * Define os dados de entrada (Input) e os dados de saída (Output)
 * utilizados na aplicação para recuperar um pedido.
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "Comando para buscar um pedido por ID")
public sealed interface FindByIdCommand extends Serializable
        permits FindByIdCommand.Input, FindByIdCommand.Output {

    /**
     * Dados de entrada para buscar um pedido.
     */
    @Schema(description = "Dados de entrada para buscar um pedido")
    record Input(
            @NotNull(message = "{pedido.id.obrigatorio}")
            @Schema(description = "Identificador único do pedido", example = "123e4567-e89b-12d3-a456-426614174000")
            UUID orderId
    ) implements FindByIdCommand {}

    /**
     * Dados de saída ao buscar um pedido.
     */
    @Schema(description = "Dados de saída da busca por pedido")
    record Output(
            @Schema(description = "Identificador único do pedido", example = "123e4567-e89b-12d3-a456-426614174000")
            UUID id,

            @Schema(description = "Identificador do cliente", example = "f9b8e5fc-3d4c-4c9d-a24f-9a6a9b4c4567")
            UUID clientId,

            @Schema(description = "Valor total do pedido", example = "250.75")
            BigDecimal totalAmount,

            @Schema(description = "Status atual do pedido", example = "PENDENTE")
            String status,

            @Schema(description = "Data e hora em que o pedido foi criado", example = "2025-05-15T10:20:30")
            LocalDateTime createdAt
    ) implements FindByIdCommand {}
}
