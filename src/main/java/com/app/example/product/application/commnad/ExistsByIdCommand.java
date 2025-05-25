package com.app.example.product.application.commnad;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * Comando para verificar a existência de um produto com base em seu ID.
 * Define os dados de entrada e saída para a operação.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public sealed interface ExistsByIdCommand extends Serializable
        permits ExistsByIdCommand.Input, ExistsByIdCommand.Output {

    /**
     * Dados de entrada para o comando de verificação de existência de produto.
     *
     * @param productId o identificador do produto
     */
    @Schema(description = "Entrada para verificar se um produto existe por ID")
    record Input(
            String productId
    ) implements ExistsByIdCommand {}

    /**
     * Resultado da verificação de existência de produto.
     *
     * @param exist indica se o produto existe
     */
    @Schema(description = "Saída indicando se o produto existe")
    record Output(
            boolean exist
    ) implements ExistsByIdCommand {}
}