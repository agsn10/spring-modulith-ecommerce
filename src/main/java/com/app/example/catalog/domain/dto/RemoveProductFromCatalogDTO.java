package com.app.example.catalog.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * Interface base para o DTO de Remoção de Produto no Catálogo.
 * <p>
 * Este DTO é utilizado para operações de remoção de um produto de um catálogo existente.
 * Ele define os formatos de entrada (requisição) e saída (resposta) da operação.
 * <p>
 * Essa interface é selada, permitindo apenas os tipos {@code Request} e {@code Response} como implementações,
 * garantindo maior segurança de tipo e clareza na estrutura da API.
 * </p>
 *
 * @see RemoveProductFromCatalogDTO.Request
 * @see RemoveProductFromCatalogDTO.Response
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "Interface base para o DTO de Remoção do Produto no Catálogo")
public sealed interface RemoveProductFromCatalogDTO extends Serializable
        permits RemoveProductFromCatalogDTO.Request, RemoveProductFromCatalogDTO.Response {

    /**
     * Dados necessários para remover um produto de um catálogo existente.
     * <p>
     * Esta estrutura carrega os identificadores necessários para localizar o catálogo
     * e o produto que será removido.
     * </p>
     *
     * @param catalogId identificador único do catálogo
     * @param productId identificador único do produto a ser removido
     */
    @Schema(description = "Dados necessários para remover um produto de um catálogo existente")
    record Request(
            @NotBlank(message = "{removeProductFromCatalog.request.catalogId.notBlank}")
            @Schema(description = "Identificador do catálogo de onde o produto será removido", example = "1")
            String catalogId,

            @NotBlank(message = "{removeProductFromCatalog.request.productId.notBlank}")
            @Schema(description = "Identificador do produto a ser removido do catálogo", example = "101")
            String productId
    ) implements RemoveProductFromCatalogDTO {}

    /**
     * Estrutura de resposta retornada após a tentativa de remoção de um produto do catálogo.
     * <p>
     * Contém a confirmação da operação e uma mensagem explicativa.
     * </p>
     *
     * @param productId identificador do produto que foi removido
     * @param message mensagem informativa sobre o resultado da operação
     */
    @Schema(description = "Resposta após a remoção de um produto do catálogo")
    record Response(
            @Schema(description = "Identificador do produto removido do catálogo", example = "101")
            String productId,

            @Schema(description = "Mensagem informando o resultado da remoção", example = "Produto removido com sucesso")
            String message
    ) implements RemoveProductFromCatalogDTO {}
}
