package com.app.example.order.application.command;

import java.io.Serializable;

/**
 * Comando de entrada e saída para o caso de uso de cancelamento de pedido.
 * <p>
 * Define os dados de entrada ({@link Input}) e de saída ({@link Output}) para a operação de cancelamento de pedido.
 * Pode ser utilizado como contrato entre as camadas de aplicação e de domínio.
 * <p>
 * Essa interface segue o padrão Command aplicado na arquitetura orientada a casos de uso.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public sealed interface CancelOrderCommand extends Serializable
        permits CancelOrderCommand.Input, CancelOrderCommand.Output {

    /**
     * Dados de entrada para o cancelamento do pedido.
     *
     * @param orderId Identificador do pedido a ser cancelado.
     */
    record Input(
            String orderId
    ) implements CancelOrderCommand {}

    /**
     * Resultado da operação de cancelamento do pedido.
     *
     * @param orderId Identificador do pedido cancelado.
     * @param message Mensagem de confirmação do cancelamento.
     */
    record Output(
            String orderId,
            String message
    ) implements CancelOrderCommand {}
}

