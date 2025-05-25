package com.app.example.product.application.commnad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Comando para listar produtos por categoria.
 * <p>
 * Representa os dados estruturados utilizados pela camada de aplicação para
 * processar a listagem de produtos com base em sua categoria.
 * </p>
 *
 * Contém os dados de entrada ({@link Input}) e de saída ({@link Output}) para o caso de uso.
 *
 * @author
 *     <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public sealed interface ListByCategoryProductCommand extends Serializable
        permits ListByCategoryProductCommand.Input, ListByCategoryProductCommand.Output {

    /**
     * Dados de entrada para o comando de listagem por categoria.
     *
     * @param category Categoria dos produtos que se deseja listar.
     */
    record Input(String category) implements ListByCategoryProductCommand {}

    /**
     * Dados de saída do comando contendo a lista de produtos da categoria.
     *
     * @param products Lista de produtos encontrados na categoria especificada.
     */
    record Output(UUID id,
                  String name,
                  String description,
                  BigDecimal price,
                  Integer stockQuantity,
                  String category) implements ListByCategoryProductCommand {}
}
