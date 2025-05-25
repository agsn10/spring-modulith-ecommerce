package com.app.example.client.infra.repository;

import com.app.example.client.domain.po.ClientPO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repositório reativo para o objeto de persistência {@link ClientPO}.
 * Usado para manipular dados de pagamentos realizados em pedidos.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Repository
public interface ClientRepository extends ReactiveCrudRepository<ClientPO, UUID>, ReactiveSortingRepository<ClientPO, UUID> {

    /**
     * Verifica se existe um cliente com o e-mail informado.
     *
     * @param email Endereço de e-mail do cliente.
     * @return Mono<Boolean> indicando se o cliente existe.
     */
    Mono<Boolean> existsByEmail(String email);
}