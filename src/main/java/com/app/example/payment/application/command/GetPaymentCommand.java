package com.app.example.payment.application.command;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Command para obter os dados de um pagamento com base no ID do pedido.
 * <p>
 * Usado como contrato interno da aplicação entre o controller/use case e a lógica de negócio.
 * </p>
 *
 * Contém uma classe interna {@link Input} com os dados necessários para realizar a consulta
 * e uma classe {@link Output} com os dados retornados do pagamento encontrado.
 *
 * @author
 *     <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public sealed interface GetPaymentCommand extends Serializable
        permits GetPaymentCommand.Input, GetPaymentCommand.Output {

    /**
     * Dados de entrada para a consulta de um pagamento.
     *
     * @param orderId ID do pedido associado ao pagamento.
     */
    record Input(String orderId) implements GetPaymentCommand {}

    /**
     * Dados de saída com os detalhes do pagamento encontrado.
     *
     * @param id      ID do pagamento.
     * @param orderId ID do pedido.
     * @param status  Status do pagamento.
     * @param method  Método utilizado no pagamento.
     * @param amount  Valor pago.
     * @param paidAt  Data e hora do pagamento.
     */
    record Output(
            UUID id,
            UUID orderId,
            String status,
            String method,
            BigDecimal amount,
            LocalDateTime paidAt
    ) implements GetPaymentCommand {}
}