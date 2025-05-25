package com.app.example.product.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO de entrada e saída para manipulação de produtos.
 * <p>
 * Representa os dados que trafegam entre a camada de apresentação e a aplicação
 * durante as operações com produtos, como criação, atualização ou visualização.
 * <p>
 * Alinhado com o contrato do caso de uso que manipula {@code Product}.
 *
 * @author
 *     <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "DTO de entrada e saída para manipulação de produtos")
public sealed interface ListByCategoryProductDTO extends Serializable
        permits ListByCategoryProductDTO.Request, ListByCategoryProductDTO.Response {

    /**
     * Dados de entrada para listar produtos pela categoria.
     *
     * @param category  Categoria do produto.
     */
    @Schema(description = "Dados de entrada para listar produtos pela categoria")
    record Request(
            @NotBlank(message = "{list.by.category.product.request.category.notBlank}")
            @Schema(description = "Categoria do produto", example = "Vestuário")
            String category
    ) implements ListByCategoryProductDTO {}

    /**
     * Dados de resposta ao consultar por categoria dos produtos.
     *
     * @param id            Identificador único do produto.
     * @param name          Nome do produto.
     * @param description   Descrição do produto.
     * @param price         Preço do produto.
     * @param stockQuantity Quantidade em estoque.
     * @param category      Categoria do produto.
     */
    @Schema(description = "Dados de resposta ao consultar ou registrar um produto")
    record Response(
            @Schema(description = "ID do produto", example = "123e4567-e89b-12d3-a456-426614174000")
            UUID id,

            @Schema(description = "Nome do produto", example = "Camiseta Polo Masculina")
            String name,

            @Schema(description = "Descrição do produto", example = "Camiseta polo de algodão, tamanho M")
            String description,

            @Schema(description = "Preço do produto", example = "79.90")
            BigDecimal price,

            @Schema(description = "Quantidade em estoque", example = "150")
            Integer stockQuantity,

            @Schema(description = "Categoria do produto", example = "Vestuário")
            String category
    ) implements ListByCategoryProductDTO {}

}