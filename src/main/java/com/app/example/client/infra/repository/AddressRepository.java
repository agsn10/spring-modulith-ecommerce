package com.app.example.client.infra.repository;

import com.app.example.client.domain.po.AddressPO;
import com.app.example.client.domain.po.ClientPO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repositório reativo para o objeto de persistência {@link AddressPO}.
 * Usado para manipular dados de pagamentos realizados em pedidos.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Repository
public interface AddressRepository extends ReactiveCrudRepository<AddressPO, UUID> {
}