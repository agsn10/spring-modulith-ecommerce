package com.app.example.product.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO para operações relacionadas à obtenção de estoque de um produto.
 * <p>
 * Define os dados de entrada (request) e de saída (response)
 * para a consulta de estoque disponível de um determinado produto.
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "DTO para consulta de estoque de um produto")
public sealed interface GetStockDTO extends Serializable
        permits GetStockDTO.Request, GetStockDTO.Response {

    /**
     * Dados de requisição para consultar o estoque de um produto.
     *
     * @param productId ID do produto a ser consultado
     */
    @Schema(description = "Dados necessários para consultar o estoque de um produto")
    record Request(
            /**
             * Identificador do produto a ser consultado.
             * Deve estar no formato UUID (36 caracteres).
             */
            @NotBlank(message = "{get.stock.request.productId.notBlank}")
            @Pattern(regexp = "^[0-9a-fA-F\\-]{36}$", message = "{get.stock.request.productId.invalidFormat}")
            @Schema(description = "ID do produto", example = "a1b2c3d4-e5f6-7890-abcd-1234567890ef", required = true)
            String productId
    ) implements GetStockDTO {}

    /**
     * Dados de resposta contendo as informações de estoque do produto consultado.
     *
     * @param productId   ID do produto consultado
     * @param quantity    Quantidade disponível em estoque
     * @param name        Nome do produto
     * @param description Descrição do produto
     * @param price       Preço unitário do produto
     * @param message     Mensagem de confirmação da operação
     */
    @Schema(description = "Resposta da operação de consulta de estoque do produto")
    record Response(
            /**
             * Identificador do produto consultado.
             */
            @Schema(description = "ID do produto", example = "a1b2c3d4-e5f6-7890-abcd-1234567890ef")
            String productId,

            /**
             * Quantidade de unidades disponíveis no estoque.
             */
            @Schema(description = "Estoque disponível", example = "50")
            int quantity,

            /**
             * Nome do produto.
             */
            @Schema(description = "Nome do produto", example = "Notebook Dell Inspiron 15")
            String name,

            /**
             * Descrição detalhada do produto.
             */
            @Schema(description = "Descrição do produto", example = "Notebook com 16GB RAM, 512GB SSD e processador Intel i7")
            String description,

            /**
             * Preço unitário do produto em reais.
             */
            @Schema(description = "Preço unitário do produto", example = "3999.90")
            BigDecimal price,

            /**
             * Mensagem de status ou confirmação da operação.
             */
            @Schema(description = "Mensagem de confirmação", example = "Estoque obtido com sucesso")
            String message
    ) implements GetStockDTO {}
}
