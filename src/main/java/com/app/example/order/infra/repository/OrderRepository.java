package com.app.example.order.infra.repository;

import com.app.example.order.domain.po.OrderPO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repositório reativo responsável pelas operações de persistência da entidade {@link OrderPO}.
 * <p>
 * Estende {@link ReactiveCrudRepository} para fornecer operações CRUD reativas com suporte
 * completo a assincronismo e não bloqueio, ideal para aplicações baseadas em WebFlux.
 * </p>
 *
 * <p>
 * O Spring Data cria automaticamente a implementação desta interface com base na assinatura dos métodos.
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Repository
public interface OrderRepository extends ReactiveCrudRepository<OrderPO, UUID> {
}