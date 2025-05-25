package com.app.example.catalog.infra.repository;

import com.app.example.catalog.domain.po.CatalogPO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repositório reativo responsável por persistir dados da entidade {@link CatalogPO}.
 *
 * @author <a href="mailto:antonio.neto@example.com">Antonio Neto</a>
 */
@Repository
public interface CatalogRepository extends ReactiveCrudRepository<CatalogPO, UUID> {
}