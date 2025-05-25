package com.app.example.invoice.application.command;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Interface base para os comandos de geração de fatura (Invoice).
 * Esta interface define os dados necessários para a criação de uma fatura, tanto os dados de entrada quanto os resultados da operação.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "Interface base para os comandos de geração de fatura (Invoice)")
public sealed interface GenerateInvoiceCommand extends Serializable
        permits GenerateInvoiceCommand.Input, GenerateInvoiceCommand.Output {

    /**
     * Dados de entrada para geração de uma fatura.
     * Contém as informações necessárias para criar uma fatura a partir de um pedido existente.
     */
    @Schema(description = "Dados de entrada para geração de uma fatura")
    record Input(
            /**
             * Identificador do pedido relacionado à fatura.
             * Este identificador é único para cada pedido e será usado para associar a fatura ao pedido.
             *
             * Exemplo: "123e4567-e89b-12d3-a456-426614174000"
             */
            @Schema(description = "Identificador do pedido relacionado à fatura", example = "123e4567-e89b-12d3-a456-426614174000")
            String orderId
    ) implements GenerateInvoiceCommand {}

    /**
     * Resultado da operação de geração da fatura.
     * Contém as informações da fatura gerada após o sucesso da operação de geração.
     */
    @Schema(description = "Resultado da operação de geração da fatura")
    record Output(
            /**
             * Identificador da fatura gerada.
             * Este identificador é único para cada fatura gerada e será usado para rastrear a fatura no sistema.
             *
             * Exemplo: "123e4567-e89b-12d3-a456-426614174999"
             */
            @Schema(description = "Identificador da fatura gerada", example = "123e4567-e89b-12d3-a456-426614174999")
            UUID invoiceId,

            /**
             * Número da fatura gerada.
             * Este número é atribuído à fatura como uma referência única e é utilizado para identificá-la de forma mais amigável.
             *
             * Exemplo: "INV-1684359876543"
             */
            @Schema(description = "Número da fatura gerada", example = "INV-1684359876543")
            String invoiceNumber,

            /**
             * Data e hora em que a fatura foi gerada.
             * Representa o momento exato em que a fatura foi criada no sistema.
             *
             * Exemplo: "2025-05-13T10:15:30Z"
             */
            @Schema(description = "Data e hora em que a fatura foi gerada", example = "2025-05-13T10:15:30Z")
            LocalDateTime generatedAt,

            /**
             * Mensagem informativa sobre o resultado da operação.
             */
            @Schema(description = "Mensagem informativa sobre o resultado da operação", example = "Fatura gerada com sucesso")
            String message
    ) implements GenerateInvoiceCommand {}
}
