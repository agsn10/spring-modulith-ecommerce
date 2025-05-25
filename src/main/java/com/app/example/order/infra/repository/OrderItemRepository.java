package com.app.example.order.infra.repository;

import com.app.example.order.domain.po.OrderItemPO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repositório reativo para acesso e manipulação dos dados da entidade {@link OrderItemPO}.
 * <p>
 * Esta interface estende {@link ReactiveCrudRepository}, fornecendo operações CRUD reativas
 * para a tabela de itens de pedido. A reatividade permite o tratamento assíncrono e não bloqueante
 * das operações de persistência.
 * </p>
 *
 * <p>
 * As implementações dos métodos são geradas automaticamente pelo Spring Data com base na
 * assinatura dos métodos e no tipo da entidade.
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Repository
public interface OrderItemRepository extends ReactiveCrudRepository<OrderItemPO, UUID> {
}
