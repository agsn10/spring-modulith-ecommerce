package com.app.example.payment.infra.repository;

import com.app.example.payment.domain.po.PaymentPO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repositório reativo para o objeto de persistência {@link PaymentPO}.
 * Usado para manipular dados de pagamentos realizados em pedidos.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Repository
public interface PaymentRepository extends ReactiveCrudRepository<PaymentPO, UUID> {

    Mono<PaymentPO> findByOrderId(UUID orderId);
}