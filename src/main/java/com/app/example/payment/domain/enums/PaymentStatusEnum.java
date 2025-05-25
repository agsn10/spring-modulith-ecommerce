package com.app.example.payment.domain.enums;

/**
 * Enum que representa os possíveis estados de um pagamento.
 * <p>
 * Pode ser utilizado para rastrear e controlar o ciclo de vida de um pagamento
 * dentro do sistema.
 * </p>
 *
 * Exemplos de uso:
 * - Para indicar se um pagamento foi aprovado ou recusado
 * - Para status intermediários como em processamento ou pendente
 *
 * @author
 *     <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public enum PaymentStatusEnum {

    /**
     * Pagamento criado mas ainda não processado.
     */
    PENDING,

    /**
     * Pagamento está em processamento.
     */
    PROCESSING,

    /**
     * Pagamento foi aprovado com sucesso.
     */
    APPROVED,

    /**
     * Pagamento foi recusado pela operadora/banco.
     */
    DECLINED,

    /**
     * Pagamento falhou por erro técnico ou falta de saldo.
     */
    FAILED,

    /**
     * Pagamento cancelado por ação do usuário ou sistema.
     */
    CANCELLED
}