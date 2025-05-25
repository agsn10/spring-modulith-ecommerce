package com.app.example.product.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO de entrada e saída para alteração de estoque de um produto.
 * <p>
 * Representa os dados que trafegam na API durante a operação de alteração de quantidade
 * de estoque de um determinado produto.
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "DTO de entrada e saída para alteração de estoque de produto")
public sealed interface ChangeStockDTO extends Serializable
        permits ChangeStockDTO.Request, ChangeStockDTO.Response {

    /**
     * Dados de entrada para alteração de estoque.
     *
     * @param productId Identificador do produto.
     * @param quantity Quantidade a ser ajustada no estoque (pode ser positiva ou negativa).
     */
    @Schema(description = "Dados de entrada para alteração de estoque")
    record Request(
            @NotBlank(message = "{change.stock.request.productId.notBlank}")
            @Schema(description = "ID do produto", example = "a1b2c3d4-e5f6-7890-abcd-1234567890ef")
            String productId,

            @NotNull(message = "{change.stock.request.quantity.notNull}")
            @Schema(description = "Quantidade a ser ajustada (positiva ou negativa)", example = "10")
            int quantity
    ) implements ChangeStockDTO {}

    /**
     * Resultado da operação de alteração de estoque.
     *
     * @param productId Identificador do produto.
     * @param newStock Quantidade atual de estoque após a operação.
     * @param message Mensagem de confirmação ou status da operação.
     */
    @Schema(description = "Resultado da operação de alteração de estoque")
    record Response(
            @Schema(description = "ID do produto", example = "a1b2c3d4-e5f6-7890-abcd-1234567890ef")
            String productId,

            @Schema(description = "Novo estoque disponível após a alteração", example = "50")
            int newStock,

            @Schema(description = "Mensagem de confirmação", example = "Estoque atualizado com sucesso")
            String message
    ) implements ChangeStockDTO {}
}
