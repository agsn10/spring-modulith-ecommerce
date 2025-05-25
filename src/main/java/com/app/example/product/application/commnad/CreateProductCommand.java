package com.app.example.product.application.commnad;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Command para criar um produto.
 * <p>
 * Estrutura baseada no padrão Command, utilizando Input para os dados de entrada
 * e Output para os dados de saída, separados da camada de apresentação.
 * <p>
 * Representa os dados trafegados entre a aplicação e o caso de uso.
 *
 * @author
 *     <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "Command para criação de produto (Input/Output)")
public sealed interface CreateProductCommand extends Serializable
        permits CreateProductCommand.Input, CreateProductCommand.Output {

    /**
     * Dados de entrada para o comando de criação de produto.
     *
     * @param name          Nome do produto.
     * @param description   Descrição do produto.
     * @param price         Preço do produto.
     * @param stockQuantity Quantidade em estoque.
     * @param category      Categoria do produto.
     */
    @Schema(description = "Dados de entrada para criação de produto")
    record Input(
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
    ) implements CreateProductCommand {}

    /**
     * Dados de saída após execução do comando de criação de produto.
     *
     * @param id      Identificador único do produto criado.
     * @param message Mensagem de confirmação ou status.
     */
    @Schema(description = "Dados de saída após criação do produto")
    record Output(
            @Schema(description = "ID do produto", example = "123e4567-e89b-12d3-a456-426614174000")
            UUID id,

            @Schema(description = "Mensagem de confirmação", example = "Produto cadastrado com sucesso")
            String message
    ) implements CreateProductCommand {}
}
