package com.app.example.catalog.infra.repository;

import com.app.example.catalog.domain.po.CatalogProductPO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repositório reativo responsável por persistir dados da entidade {@link CatalogProductPO}.
 *
 * @author <a href="mailto:antonio.neto@example.com">Antonio Neto</a>
 */
@Repository
public interface CatalogProductRepository extends ReactiveCrudRepository<CatalogProductPO, UUID> {

    @Query("DELETE FROM catalog_products WHERE catalog_id = :catalogId AND product_id = :productId")
    Mono<Void> deleteByCatalogIdAndProductId(UUID catalogId, UUID productId);
}