package com.app.example.payment.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO de entrada e saída para a obtenção de um pagamento existente.
 * <p>
 * Utilizado para consultar os dados de um pagamento baseado no ID do pedido.
 * Alinhado ao modelo de domínio {@link com.app.example.payment.domain.po.PaymentPO}.
 * </p>
 *
 * @author
 *     <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "DTO de entrada e saída para obtenção de pagamento por ID de pedido")
public sealed interface GetPaymentDTO extends Serializable
        permits GetPaymentDTO.Request, GetPaymentDTO.Response {

    /**
     * Dados de entrada para buscar um pagamento baseado no ID do pedido.
     *
     * @param orderId ID do pedido relacionado ao pagamento.
     */
    @Schema(description = "Dados de entrada para buscar um pagamento")
    record Request(
            @NotBlank(message = "{get.payment.request.orderId.notBlank}")
            @Schema(description = "ID do pedido", example = "order-1234")
            String orderId
    ) implements GetPaymentDTO {}

    /**
     * Dados de saída com informações completas do pagamento.
     *
     * @param id       ID do pagamento.
     * @param orderId  ID do pedido associado.
     * @param status   Status atual do pagamento.
     * @param method   Método utilizado no pagamento.
     * @param amount   Valor pago.
     * @param paidAt   Data e hora do pagamento.
     */
    @Schema(description = "Dados de resposta com os detalhes do pagamento")
    record Response(
            @Schema(description = "ID do pagamento", example = "payment-5678")
            UUID id,

            @Schema(description = "ID do pedido", example = "order-1234")
            UUID orderId,

            @Schema(description = "Status do pagamento", example = "APPROVED")
            String status,

            @Schema(description = "Método de pagamento", example = "CREDIT_CARD")
            String method,

            @Schema(description = "Valor pago", example = "150.00")
            BigDecimal amount,

            @Schema(description = "Data e hora do pagamento", example = "2024-05-19T15:30:00")
            LocalDateTime paidAt
    ) implements GetPaymentDTO {}
}
