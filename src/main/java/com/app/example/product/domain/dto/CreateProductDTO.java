package com.app.example.product.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

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
public sealed interface CreateProductDTO extends Serializable
        permits CreateProductDTO.Request, CreateProductDTO.Response {

    /**
     * Dados de entrada para criar ou atualizar um produto.
     *
     * @param name          Nome do produto.
     * @param description   Descrição do produto.
     * @param price         Preço do produto.
     * @param stockQuantity Quantidade em estoque.
     * @param category      Categoria do produto.
     */
    @Schema(description = "Dados de entrada para criar ou atualizar um produto")
    record Request(
            @NotBlank(message = "{create.product.request.name.notBlank}")
            @Size(max = 100, message = "{create.product.request.name.size}")
            @Schema(description = "Nome do produto", example = "Camiseta Polo Masculina")
            String name,

            @NotBlank(message = "{create.product.request.description.notBlank}")
            @Size(max = 500, message = "{create.product.request.description.size}")
            @Schema(description = "Descrição do produto", example = "Camiseta polo de algodão, tamanho M")
            String description,

            @NotNull(message = "{create.product.request.price.notNull}")
            @DecimalMin(value = "0.0", inclusive = false, message = "{create.product.request.price.positive}")
            @Digits(integer = 10, fraction = 2, message = "{create.product.request.price.format}")
            @Schema(description = "Preço do produto", example = "79.90")
            BigDecimal price,

            @NotNull(message = "{create.product.request.stockQuantity.notNull}")
            @Min(value = 0, message = "{create.product.request.stockQuantity.min}")
            @Schema(description = "Quantidade em estoque", example = "150")
            Integer stockQuantity,

            @NotBlank(message = "{create.product.request.category.notBlank}")
            @Size(max = 50, message = "{create.product.request.category.size}")
            @Schema(description = "Categoria do produto", example = "Vestuário")
            String category
    ) implements CreateProductDTO {}

    /**
     * Dados de resposta ao consultar ou registrar um produto.
     *
     * @param id            Identificador único do produto.
     * @param message       Mensagem de confirmação ou feedback da operação.
     */
    @Schema(description = "Dados de resposta ao consultar ou registrar um produto")
    record Response(
            @Schema(description = "ID do produto", example = "123e4567-e89b-12d3-a456-426614174000")
            String id,

            @Schema(description = "Mensagem de confirmação", example = "Produto cadastrado com sucesso")
            String message
    ) implements CreateProductDTO {}

}
