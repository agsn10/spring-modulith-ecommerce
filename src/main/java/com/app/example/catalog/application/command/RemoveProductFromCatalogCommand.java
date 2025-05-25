package com.app.example.catalog.application.command;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * Interface base para o Command de Remoção de Produto no Catálogo.
 * <p>
 * Este command encapsula os dados necessários para executar a operação de remoção de um produto
 * de um catálogo existente e o resultado da operação.
 * <p>
 * O uso de {@code Input} e {@code Output} reflete o padrão de comandos, sendo o {@code Input} os dados de entrada
 * necessários para a execução, e o {@code Output} a resposta da operação.
 * </p>
 *
 * @see RemoveProductFromCatalogCommand.Input
 * @see RemoveProductFromCatalogCommand.Output
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "Command para remoção de produto do catálogo")
public sealed interface RemoveProductFromCatalogCommand extends Serializable
        permits RemoveProductFromCatalogCommand.Input, RemoveProductFromCatalogCommand.Output {

    /**
     * Dados de entrada necessários para executar a remoção de um produto de um catálogo.
     *
     * @param catalogId identificador único do catálogo
     * @param productId identificador único do produto
     */
    @Schema(description = "Dados de entrada para remoção de produto do catálogo")
    record Input(
            @Schema(description = "Identificador do catálogo", example = "1")
            String catalogId,

            @Schema(description = "Identificador do produto a ser removido", example = "101")
            String productId
    ) implements RemoveProductFromCatalogCommand {}

    /**
     * Resultado da execução da remoção do produto do catálogo.
     *
     * @param productId identificador do produto removido
     * @param message mensagem explicando o resultado da operação
     */
    @Schema(description = "Resultado da remoção de produto do catálogo")
    record Output(
            @Schema(description = "Identificador do produto removido", example = "101")
            String productId,

            @Schema(description = "Mensagem de retorno da operação", example = "Produto removido com sucesso")
            String message
    ) implements RemoveProductFromCatalogCommand {}
}
