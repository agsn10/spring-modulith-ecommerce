package com.app.example.order.application.command;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Comando para operações relacionadas à criação de pedidos.
 * <p>
 * Define os dados de entrada (input) e os dados de saída (output)
 * para a execução do comando de criação de um novo pedido.
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public sealed interface CreateOrderCommand extends Serializable
        permits CreateOrderCommand.Input, CreateOrderCommand.Output {

    /**
     * Dados de entrada para o comando de criação de um novo pedido.
     */
    record Input(
            /**
             * Identificador do cliente que está realizando o pedido.
             */
            UUID clientId,

            /**
             * Lista de produtos do pedido com suas respectivas quantidades.
             */
            List<ProductQuantity> products

    ) implements CreateOrderCommand {

        /**
         * Representa um produto e sua quantidade associada.
         */
        public record ProductQuantity(
                UUID productId,
                int quantity
        ) {}
    }

    /**
     * Dados de saída do comando após a criação do pedido.
     */
    record Output(
            /**
             * Identificador único do pedido criado.
             */
            UUID id,
            /**
             * Identificador do cliente que realizou o pedido.
             */
            UUID clientId,
            /**
             * Valor total do pedido.
             */
            BigDecimal totalAmount,
            /**
             * Status atual do pedido.
             */
            String status,
            /**
             * Data e hora em que o pedido foi criado.
             */
            LocalDateTime createdAt
    ) implements CreateOrderCommand {}
}
