package com.app.example.invoice.infra.repository;

import com.app.example.invoice.domain.po.InvoicePO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repositório reativo da entidade {@link InvoicePO}.
 * Gerencia o armazenamento de faturas geradas a partir de pedidos.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Repository
public interface InvoiceRepository extends ReactiveCrudRepository<InvoicePO, UUID> {
}
