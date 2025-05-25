package com.app.example.order.application.command;

import java.io.Serializable;

/**
 * Comando de entrada e saída para o caso de uso de marcação de pedido como pago.
 * <p>
 * Define os dados de entrada ({@link Input}) e de saída ({@link Output}) para a operação de marcação como pago.
 * Pode ser utilizado como contrato entre as camadas de aplicação e de domínio.
 * <p>
 * Essa interface segue o padrão Command aplicado na arquitetura orientada a casos de uso.
 *
 * @autor Antonio Neto
 */
public sealed interface MarkOrderAsPaidCommand extends Serializable
        permits MarkOrderAsPaidCommand.Input, MarkOrderAsPaidCommand.Output {

    /**
     * Dados de entrada para a marcação do pedido como pago.
     *
     * @param orderId Identificador do pedido a ser marcado como pago.
     */
    record Input(
            String orderId
    ) implements MarkOrderAsPaidCommand {}

    /**
     * Resultado da operação de marcação como pago.
     *
     * @param orderId Identificador do pedido que foi marcado como pago.
     * @param message Mensagem de confirmação da operação.
     */
    record Output(
            String orderId,
            String message
    ) implements MarkOrderAsPaidCommand {}
}

