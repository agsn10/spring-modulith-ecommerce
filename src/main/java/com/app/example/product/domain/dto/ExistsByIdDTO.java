package com.app.example.product.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

/**
 * DTO para operações relacionadas à existência de um produto.
 * <p>
 * Define os dados de entrada (request) e de saída (response)
 * para a consulta se o produto existe.
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "DTO para consulta se o produto existe")
public sealed interface ExistsByIdDTO extends Serializable
        permits ExistsByIdDTO.Request, ExistsByIdDTO.Response {

    /**
     * Dados de requisição para consultar o estoque de um produto.
     *
     * @param productId ID do produto a ser consultado
     */
    @Schema(description = "Dados necessários para consultar o estoque de um produto")
    record Request(
            /**
             * Identificador do produto a ser consultado.
             */
            @NotBlank(message = "{get.stock.request.productId.notBlank}")
            @Pattern(regexp = "^[0-9a-fA-F\\-]{36}$", message = "{get.stock.request.productId.invalidFormat}")
            @Schema(description = "ID do produto", example = "a1b2c3d4-e5f6-7890-abcd-1234567890ef")
            String productId
    ) implements ExistsByIdDTO {}

    /**
     * Dados de resposta contendo as informações de estoque do produto consultado.
     *
     * @param message Mensagem de confirmação
     */
    @Schema(description = "Resposta da operação de consulta se o produto existe")
    record Response(
            @Schema(description = "Confirmação se o produto existe", example = "true")
            boolean exist
    ) implements ExistsByIdDTO {}
}
