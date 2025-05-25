package com.app.example.product.application.commnad;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * Comando que encapsula os dados necessários para executar a alteração de estoque de um produto.
 * <p>
 * Utilizado pelos casos de uso para representar a intenção de mudar a quantidade de um produto no estoque.
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "Comando para alteração de estoque de produto")
public sealed interface ChangeStockCommand extends Serializable
        permits ChangeStockCommand.Input, ChangeStockCommand.Output {

    /**
     * Dados de entrada para alteração de estoque.
     *
     * @param productId Identificador do produto.
     * @param quantity Quantidade a ser ajustada no estoque (positiva ou negativa).
     */
    @Schema(description = "Entrada para alteração de estoque")
    record Input(
            @Schema(description = "ID do produto", example = "a1b2c3d4-e5f6-7890-abcd-1234567890ef")
            String productId,

            @Schema(description = "Quantidade a ser ajustada (positiva ou negativa)", example = "-3")
            int quantity
    ) implements ChangeStockCommand {}

    /**
     * Resultado da operação de alteração de estoque.
     *
     * @param productId Identificador do produto.
     * @param newStock Quantidade atual de estoque após a operação.
     * @param message Mensagem de confirmação.
     */
    @Schema(description = "Resultado da operação de alteração de estoque")
    record Output(
            @Schema(description = "ID do produto", example = "a1b2c3d4-e5f6-7890-abcd-1234567890ef")
            String productId,

            @Schema(description = "Novo valor do estoque após a alteração", example = "7")
            int newStock,

            @Schema(description = "Mensagem de status da operação", example = "Estoque atualizado com sucesso")
            String message
    ) implements ChangeStockCommand {}
}

