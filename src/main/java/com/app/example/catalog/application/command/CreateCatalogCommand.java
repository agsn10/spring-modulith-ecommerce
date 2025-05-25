package com.app.example.catalog.application.command;

import java.io.Serializable;
import java.util.List;

/**
 * Comando responsável por encapsular os dados de entrada e saída
 * utilizados no caso de uso de criação de um catálogo de produtos.
 *
 * Este comando segue o padrão Command Pattern para promover
 * a separação de responsabilidades e reutilização.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public sealed interface CreateCatalogCommand extends Serializable
        permits CreateCatalogCommand.Input, CreateCatalogCommand.Output {

    /**
     * Dados de entrada para criação de um novo catálogo de produtos.
     * Contém o nome do catálogo e a lista de produtos associados.
     */
    record Input(
            String name,
            List<String> productList
    ) implements CreateCatalogCommand {}

    /**
     * Dados de saída retornados após a criação do catálogo.
     * Contém o ID do catálogo criado e uma mensagem descritiva do resultado.
     */
    record Output(
            String id,
            String message
    ) implements CreateCatalogCommand {}
}
