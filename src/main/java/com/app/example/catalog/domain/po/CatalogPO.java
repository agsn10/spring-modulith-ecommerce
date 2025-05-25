package com.app.example.catalog.domain.po;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.UUID;

/**
 * Objeto de persistência que representa um catálogo de produtos no sistema.
 *
 * Mapeada para a tabela {@code catalogs} no banco de dados relacional.
 * Utiliza anotações do Spring Data para suporte à persistência reativa ou JDBC.
 *
 * Cada catálogo possui um identificador único, um nome e uma lista de identificadores
 * de produtos associados a ele.
 *
 * @author <a href="mailto:antonio.neto@example.com">Antonio Neto</a>
 */
@Table("catalogs")
public class CatalogPO {
    /**
     * Identificador único do catálogo.
     */
    @Id
    private UUID id;
    /**
     * Nome do catálogo.
     */
    private String name;

    public CatalogPO() {
    }

    public CatalogPO(String name) {
        this.name = name;
    }

    public CatalogPO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}