package com.app.example.catalog.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * Interface base para o DTO de Adição de Produto no Catálogo.
 * Este DTO é usado para adicionar produtos a um catálogo existente,
 * fornecendo os dados necessários para associar um produto a um catálogo.
 *
 * Essa interface é selada e define os contratos possíveis para as operações
 * relacionadas à adição de produtos no catálogo.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "Interface base para o DTO de Adição de Produto no Catálogo")
public sealed interface AddProductToCatalogDTO extends Serializable
        permits AddProductToCatalogDTO.Request, AddProductToCatalogDTO.Response {

    /**
     * Dados para adicionar um produto a um catálogo existente.
     * Contém as informações necessárias para associar um produto a um catálogo,
     * incluindo os identificadores do catálogo e do produto.
     *
     * @param catalogId identificador do catálogo
     * @param productId identificador do produto
     */
    @Schema(description = "Dados para adicionar um produto a um catálogo existente")
    record Request(
            /**
             * Identificador do catálogo ao qual o produto será adicionado.
             * Refere-se ao catálogo existente no qual o produto será inserido.
             *
             * Exemplo: 1
             */
            @NotBlank(message = "{addProductToCatalog.request.catalogId.notBlank}")
            @Schema(description = "Identificador do catálogo ao qual o produto será adicionado", example = "1")
            String catalogId,

            /**
             * Lista de produtos que compõem o catálogo.
             * Contém os produtos que serão associados ao catálogo.
             *
             * Exemplo: [{ "id": 101, "name": "Smartphone 5G", ...}]
             */
            @NotEmpty(message = "{createCatalog.request.productList.notEmpty}")
            @Schema(description = "Lista de produtos que compõem o catálogo")
            List<String> productList
    ) implements AddProductToCatalogDTO {}

    /**
     * Resposta da operação de adição de produto ao catálogo.
     *
     * @param productId identificador do produto que foi adicionado
     * @param message mensagem informando o resultado da operação
     */
    @Schema(description = "Resposta da operação de adição de produto ao catálogo")
    record Response(
            /**
             * Identificador do produto que foi adicionado ao catálogo.
             *
             * Exemplo: 101
             */
            @Schema(description = "Identificador do produto adicionado ao catálogo", example = "101")
            String productId,

            /**
             * Mensagem indicando o resultado da adição.
             *
             * Exemplo: "Produto adicionado com sucesso ao catálogo"
             */
            @Schema(description = "Mensagem indicando o resultado da operação", example = "Produto adicionado com sucesso ao catálogo")
            String message
    ) implements AddProductToCatalogDTO {}
}
