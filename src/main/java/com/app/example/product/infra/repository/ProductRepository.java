package com.app.example.product.infra.repository;

import com.app.example.product.domain.po.ProductPO;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repositório reativo para a entidade {@link ProductPO}.
 * Fornece operações de acesso a dados para produtos.
 */
@Repository
public interface ProductRepository extends ReactiveCrudRepository<ProductPO, UUID> {
    /**
     * Atualiza a quantidade em estoque de um produto com base no seu ID.
     *
     * @param productId ID do produto.
     * @param quantity Quantidade a ser ajustada (positiva ou negativa).
     * @return Mono com o número de linhas afetadas.
     */
    @Modifying
    @Query("UPDATE products SET stock_quantity = stock_quantity - :quantity WHERE id = :productId")
    Mono<Integer> updateStockQuantity(int quantity, UUID productId);


    /**
     * Recupera todos os produtos filtrados pela categoria.
     *
     * @param category Categoria dos produtos.
     * @return Fluxo com todos os produtos da categoria informada.
     */
    @Query("SELECT * FROM products WHERE category = :category")
    Flux<ProductPO> findAllByCategory(String category);
}
