package com.app.example.order.application.command;

import java.io.Serializable;

/**
 * Comando de entrada e saída para o caso de uso de envio de pedido.
 * <p>
 * Define os dados de entrada ({@link Input}) e de saída ({@link Output}) para a operação de envio de pedido.
 * Utilizado como contrato entre as camadas de aplicação e domínio.
 *
 * @author Antonio Neto
 */
public sealed interface ShipOrderCommand extends Serializable
        permits ShipOrderCommand.Input, ShipOrderCommand.Output {

    /**
     * Dados de entrada para envio do pedido.
     *
     * @param orderId Identificador do pedido a ser enviado.
     */
    record Input(
            String orderId
    ) implements ShipOrderCommand {}

    /**
     * Resultado da operação de envio do pedido.
     *
     * @param orderId Identificador do pedido enviado.
     * @param message Mensagem de confirmação do envio.
     */
    record Output(
            String orderId,
            String message
    ) implements ShipOrderCommand {}
}

