package com.app.example.invoice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para geração de faturas (Invoice).
 * <p>
 * A interface fornece os dados de entrada (request) e de saída (response)
 * para a geração de uma fatura.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "DTO para geração de fatura (Invoice)")
public sealed interface GenerateInvoiceDTO extends Serializable
        permits GenerateInvoiceDTO.Request, GenerateInvoiceDTO.Response {

    /**
     * Dados de requisição para geração de uma fatura.
     * Representa os dados necessários para solicitar a criação de uma fatura.
     *
     * @param orderId     Identificador do pedido relacionado à fatura.
     * @param totalAmount Valor total do pedido que será faturado.
     */
    @Schema(description = "Dados de entrada para geração de uma fatura")
    record Request(
            /**
             * Identificador do pedido relacionado à fatura.
             */
            @NotBlank(message = "{generateInvoice.request.orderId.notBlank}")
            @Pattern(
                    regexp = "^[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[1-5][0-9a-fA-F]{3}\\-[89abAB][0-9a-fA-F]{3}\\-[0-9a-fA-F]{12}$",
                    message = "{generateInvoice.request.orderId.pattern}"
            )
            @Schema(description = "Identificador do pedido relacionado à fatura", example = "123e4567-e89b-12d3-a456-426614174000")
            String orderId
    ) implements GenerateInvoiceDTO {}

    /**
     * Dados de resposta após a geração da fatura.
     * Representa os dados retornados após a criação bem-sucedida de uma fatura.
     *
     * @param invoiceId     Identificador único da fatura gerada.
     * @param invoiceNumber Número da fatura gerada, no formato padrão da empresa.
     * @param generatedAt   Data e hora exatas da geração da fatura.
     * @param message       Mensagem informativa sobre o resultado da operação.
     */
    @Schema(description = "Resultado da operação de geração da fatura")
    record Response(
            /**
             * Identificador da fatura gerada.
             */
            @Schema(description = "Identificador da fatura gerada", example = "123e4567-e89b-12d3-a456-426614174999")
            UUID invoiceId,

            /**
             * Número da fatura gerada.
             */
            @Schema(description = "Número da fatura gerada", example = "INV-1684359876543")
            String invoiceNumber,

            /**
             * Data e hora em que a fatura foi gerada.
             */
            @Schema(description = "Data e hora em que a fatura foi gerada", example = "2025-05-13T10:15:30Z")
            LocalDateTime generatedAt,

            /**
             * Mensagem informativa sobre o resultado da operação.
             */
            @Schema(description = "Mensagem informativa sobre o resultado da operação", example = "Fatura gerada com sucesso")
            String message
    ) implements GenerateInvoiceDTO {}
}
