package com.app.example.order.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para consulta de pedido por ID.
 * <p>
 * Define os dados de entrada (request) e os dados de saída (response)
 * para a operação de busca de um pedido específico pelo seu ID.
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "DTO para buscar um pedido por ID")
public sealed interface FindByIdDTO extends Serializable
        permits FindByIdDTO.Request, FindByIdDTO.Response {

    /**
     * Dados de requisição para consulta de um pedido por ID.
     */
    @Schema(description = "Dados para buscar um pedido por ID")
    record Request(
            @NotNull(message = "{pedido.id.obrigatorio}")
            @Schema(description = "Identificador único do pedido", example = "123e4567-e89b-12d3-a456-426614174000")
            UUID orderId
    ) implements FindByIdDTO {}

    /**
     * Dados de resposta da consulta de pedido por ID.
     */
    @Schema(description = "Resposta da operação de busca do pedido")
    record Response(
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
    ) implements FindByIdDTO {}
}
