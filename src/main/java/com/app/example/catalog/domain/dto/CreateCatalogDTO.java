package com.app.example.catalog.domain.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

/**
 * Interface base para os DTOs de Criação de Catálogo.
 * Este DTO é utilizado para criar um novo catálogo de produtos, contendo informações sobre o catálogo
 * e os produtos associados a ele.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "Interface base para os DTOs de Criação de Catalogo")
public sealed interface CreateCatalogDTO extends Serializable
        permits CreateCatalogDTO.Request, CreateCatalogDTO.Response {

    /**
     * Dados para criação de um novo catálogo de produtos.
     * Contém as informações necessárias para criar um catálogo e associar os produtos a ele.
     */
    @Schema(description = "Dados para criação de um novo catálogo de produtos")
    record Request(
            /**
             * Nome do catálogo.
             * O nome que será atribuído ao catálogo.
             *
             * Exemplo: "Catálogo de Eletrônicos"
             */
            @NotBlank(message = "{createCatalog.request.name.notBlank}")
            @Schema(description = "Nome do catálogo", example = "Catálogo de Eletrônicos")
            String name,

            /**
             * Lista de produtos que compõem o catálogo.
             * Contém os produtos que serão associados ao catálogo.
             *
             * Exemplo: [{ "id": 101, "name": "Smartphone 5G", ...}]
             */
            @Schema(description = "Lista de produtos que compõem o catálogo")
            List<String> productList
    ) implements CreateCatalogDTO {}

    /**
     * Resposta da operação de criação do catálogo.
     * Retorna uma mensagem de sucesso ou erro e um token relacionado à operação.
     */
    @Schema(description = "Resposta da operação de criação do catálogo")
    record Response(
            /**
             * Identificador único do catálogo.
             * Este ID será usado para identificar de forma exclusiva o catálogo a ser criado.
             *
             * Exemplo: "1"
             */
            @Schema(description = "Identificador único do catálogo", example = "1")
            String id,

            /**
             * Mensagem informando o resultado da criação do catálogo.
             * Exemplo de mensagem: "Catálogo criado com sucesso".
             *
             * Exemplo: "Catálogo criado com sucesso"
             */
            @Schema(description = "Mensagem informando o resultado da criação", example = "Catálogo criado com sucesso")
            String message
    ) implements CreateCatalogDTO {}
}