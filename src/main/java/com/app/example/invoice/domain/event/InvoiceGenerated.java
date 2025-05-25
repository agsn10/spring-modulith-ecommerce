package com.app.example.invoice.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Evento de domínio que representa a geração de uma fatura.
 * Esse evento é publicado após a fatura ser criada com sucesso.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public record InvoiceGenerated(
        UUID invoiceId,
        UUID orderId,
        LocalDateTime generatedAt
) {}