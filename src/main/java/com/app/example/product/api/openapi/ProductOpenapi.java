package com.app.example.product.api.openapi;

import com.app.example.product.domain.dto.CreateProductDTO;
import com.app.example.product.domain.dto.ListByCategoryProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(
        name = "Produto API",
        description = "Operações relacionadas aos produtos"
)
public interface ProductOpenapi {

    @Operation(
            summary = "Criar um novo produto",
            description = "Cria um novo produto com os dados fornecidos no corpo da requisição",
            operationId = "createProduct",
            tags = {"Produto API"},
            requestBody = @RequestBody(
                    description = "Dados do produto a ser criado",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateProductDTO.Request.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Produto criado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreateProductDTO.Response.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados inválidos"
                    )
            }
    )
    Mono<CreateProductDTO.Response> create(CreateProductDTO.Request request);

    @Operation(
            summary = "Listar produtos por categoria",
            description = "Retorna uma lista de produtos pertencentes à categoria especificada",
            operationId = "listProductsByCategory",
            tags = {"Produto API"},
            parameters = {
                    @Parameter(
                            name = "category",
                            description = "Categoria dos produtos a serem listados",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de produtos retornada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ListByCategoryProductDTO.Response.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Categoria não encontrada"
                    )
            }
    )
    Flux<ListByCategoryProductDTO.Response> listByCategory(String category);
}
