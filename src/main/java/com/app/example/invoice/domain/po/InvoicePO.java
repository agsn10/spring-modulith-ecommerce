package com.app.example.invoice.domain.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Objeto de persistência que representa uma fatura (Invoice) no banco de dados.
 * <p>
 * Mapeada para a tabela {@code invoices}.
 * Esta classe é usada para armazenar informações relacionadas a uma fatura gerada
 * a partir de um pedido (order).
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Table("invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InvoicePO {

    /**
     * Identificador único da fatura.
     */
    @Id
    private UUID id;
    /**
     * Identificador do pedido associado a esta fatura.
     */
    @Column("order_id")
    private UUID orderId;
    /**
     * Número da fatura gerado para exibição.
     */
    @Column("invoice_number")
    private String invoiceNumber;
    /**
     * Data e hora em que a fatura foi gerada.
     */
    @Column("generated_at")
    private LocalDateTime generatedAt;
    /**
     * Valor total da fatura.
     */
    @Column("total_amount")
    private BigDecimal totalAmount;
}
