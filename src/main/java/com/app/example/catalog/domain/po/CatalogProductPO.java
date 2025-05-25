package com.app.example.catalog.domain.po;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

/**
 * Entidade que representa a relação muitos-para-muitos entre catálogo e produtos.
 * Esta entidade modela a tabela de junção {@code catalog_products} no banco de dados,
 * que associa produtos a catálogos específicos.
 *
 * Cada instância representa uma associação única entre um catálogo e um produto.
 * A chave primária composta é formada por {@code catalogId} e {@code productId}.
 *
 * Essa classe é útil para persistência com R2DBC.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Getter
@Setter
@Table("catalog_products")
public class CatalogProductPO {

    @Id
    private UUID id;
    /**
     * Identificador do catálogo associado.
     */
    private UUID catalogId;
    /**
     * Identificador do produto associado.
     */
    private UUID productId;

    public CatalogProductPO() {}

    public CatalogProductPO(UUID catalogId, UUID productId) {
        this.catalogId = catalogId;
        this.productId = productId;
    }
}
