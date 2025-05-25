package com.app.example.catalog.application.command;

import java.io.Serializable;
import java.util.List;

/**
 * Comando responsável por encapsular os dados de entrada e saída
 * utilizados no caso de uso de adição de um produto a um catálogo.
 *
 * Este comando segue o padrão Command Pattern para promover
 * a separação de responsabilidades e reutilização.
 *
 * A saída é opcional, podendo ser usada para representar o sucesso da operação.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public sealed interface AddProductToCatalogCommand extends Serializable
        permits AddProductToCatalogCommand.Input, AddProductToCatalogCommand.Output {

    /**
     * Dados de entrada para adicionar um produto a um catálogo existente.
     */
    record Input(
            String catalogId,
            List<String> productList
    ) implements AddProductToCatalogCommand {}

    /**
     * Saída da operação de adição de produto ao catálogo.
     * Pode conter mensagem, status, ou outros metadados.
     * Pode ser substituído por Void se não for necessário retorno específico.
     */
    record Output(
            List<String> productList,
            String message
    ) implements AddProductToCatalogCommand {}
}
