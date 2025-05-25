package com.app.example.order.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO para operações relacionadas à criação de pedidos.
 * <p>
 * Define os dados de entrada (request) e os dados de saída (response)
 * para a criação de um novo pedido.
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "DTO para criação de pedido")
public sealed interface CreateOrderDTO extends Serializable
        permits CreateOrderDTO.Request, CreateOrderDTO.Response {

    /**
     * Dados de requisição para criação de um novo pedido.
     */
    @Schema(description = "Dados necessários para criar um novo pedido")
    public record Request(
            @NotNull(message = "{cliente.id.obrigatorio}")
            @Schema(description = "Identificador do cliente", example = "f9b8e5fc-3d4c-4c9d-a24f-9a6a9b4c4567")
            UUID clientId,

            @NotEmpty(message = "{pedido.produtos.vazio}")
            @Schema(description = "Lista de produtos do pedido")
            List<ProductQuantity> products
    ) implements CreateOrderDTO {

        @Schema(description = "Produto e quantidade desejada")
        public static record ProductQuantity(
                @NotNull(message = "{produto.id.obrigatorio}")
                @Schema(description = "ID do produto", example = "c3f1e2b5-7d1b-4e09-8f9e-7a8b91b3b22d")
                UUID productId,

                @Min(value = 1, message = "{produto.quantidade.minima}")
                @Schema(description = "Quantidade do produto", example = "2")
                int quantity
        ) {}
    }

    /**
     * Dados de resposta após a criação do pedido.
     */
    @Schema(description = "Resposta da operação de criação do pedido")
    record Response(
            /**
             * Identificador único do pedido criado.
             */
            @Schema(description = "Identificador único do pedido criado", example = "123e4567-e89b-12d3-a456-426614174000")
            UUID id,
            /**
             * Identificador do cliente que realizou o pedido.
             */
            @Schema(description = "Identificador do cliente", example = "f9b8e5fc-3d4c-4c9d-a24f-9a6a9b4c4567")
            UUID clientId,
            /**
             * Valor total do pedido.
             */
            @Schema(description = "Valor total do pedido", example = "250.75")
            BigDecimal totalAmount,
            /**
             * Status atual do pedido.
             */
            @Schema(description = "Status atual do pedido", example = "PENDENTE")
            String status,
            /**
             * Data e hora em que o pedido foi criado.
             */
            @Schema(description = "Data e hora em que o pedido foi criado", example = "2025-05-15T10:20:30")
            LocalDateTime createdAt
    ) implements CreateOrderDTO {}

}

