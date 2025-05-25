package com.app.example.product.application.commnad;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Comando para operações relacionadas à obtenção de estoque de um produto.
 * <p>
 * Define os dados de entrada ({@link Input}) e de saída ({@link Output})
 * para o processo de obtenção de estoque no domínio da aplicação.
 * </p>
 *
 * Este comando pode ser utilizado por um caso de uso ou serviço de aplicação
 * para buscar informações sobre o estoque de um produto específico.
 *
 * Exemplo de uso:
 * <pre>{@code
 *     GetStockCommand.Input input = new GetStockCommand.Input("a1b2c3...");
 *     GetStockCommand.Output output = stockUseCase.handle(input);
 * }</pre>
 *
 * @see Input
 * @see Output
 * @see com.app.example.product.application.usecase.GetStockUseCase
 *
 * @since 1.0
 * @version 1.0
 *
 * @author Antonio Neto
 */
public sealed interface GetStockCommand extends Serializable
        permits GetStockCommand.Input, GetStockCommand.Output {

    /**
     * Dados de entrada para o comando de obtenção de estoque.
     *
     * @param productId Identificador único do produto.
     */
    record Input(
            /**
             * Identificador único do produto a ser consultado.
             */
            String productId
    ) implements GetStockCommand {}

    /**
     * Dados de saída do comando de obtenção de estoque.
     *
     * @param productId  ID do produto consultado
     * @param price      Preço unitário do produto
     * @param name       Nome do produto
     * @param description Descrição do produto
     * @param quantity   Quantidade disponível em estoque
     * @param message    Mensagem descritiva da operação realizada
     */
    record Output(
            /**
             * Identificador único do produto consultado.
             */
            String productId,

            /**
             * Preço unitário atual do produto.
             */
            BigDecimal price,

            /**
             * Nome do produto.
             */
            String name,

            /**
             * Descrição detalhada do produto.
             */
            String description,

            /**
             * Quantidade disponível no estoque.
             */
            int quantity,

            /**
             * Mensagem descritiva da operação realizada, geralmente usada para indicar sucesso ou erro.
             */
            String message
    ) implements GetStockCommand {}
}
