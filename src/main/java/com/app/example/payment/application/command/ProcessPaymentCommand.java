package com.app.example.payment.application.command;

import java.io.Serializable;

/**
 * Comando de entrada e saída para o processamento de pagamento.
 * <p>
 * Utilizado internamente pela aplicação para representar os dados necessários
 * para iniciar e responder ao processamento de um pagamento.
 * <p>
 * Substitui o uso de DTOs com anotações de Swagger em contextos não relacionados à API.
 *
 * @author
 *     <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public sealed interface ProcessPaymentCommand extends Serializable
        permits ProcessPaymentCommand.Input, ProcessPaymentCommand.Output {

    /**
     * Dados de entrada para processar um pagamento.
     *
     * @param orderId ID do pedido relacionado ao pagamento.
     * @param method  Método de pagamento utilizado.
     */
    record Input(
            String orderId,
            String method
    ) implements ProcessPaymentCommand {}

    /**
     * Dados de saída após o processamento do pagamento.
     *
     * @param id      Identificador do pagamento processado.
     * @param message Mensagem de status do processamento.
     */
    record Output(
            String id,
            String message
    ) implements ProcessPaymentCommand {}
}