package com.app.example.catalog.api.openapi;

import com.app.example.catalog.domain.dto.AddProductToCatalogDTO;
import com.app.example.catalog.domain.dto.CreateCatalogDTO;
import com.app.example.catalog.domain.dto.RemoveProductFromCatalogDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.List;

@Tag(name = "Catalog API", description = "Operações relacionadas ao gerenciamento de catálogos e produtos no catálogo")
public interface CatalogOpenApi {

    @Operation(
            summary = "Criar um novo catálogo",
            tags = {"Catalog API"},
            operationId = "create",
            description = "Cria um catálogo com as informações fornecidas",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Catálogo criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateCatalogDTO.Response.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida"
                    )
            },
            requestBody = @RequestBody(
                    description = "Informações necessárias para criar um novo catálogo",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateCatalogDTO.Request.class)
                    )
            )
    )
    public Mono<CreateCatalogDTO.Response> create(CreateCatalogDTO.Request request);

    @Operation(
            summary = "Adicionar produtos ao catálogo",
            tags = {"Catalog API"},
            operationId = "addProduct",
            description = "Adiciona uma lista de produtos ao catálogo com base no ID do catálogo fornecido.",
            parameters = {
                    @Parameter(
                            name = "catalogId",
                            description = "ID do catálogo ao qual os produtos serão adicionados",
                            required = true,
                            in = ParameterIn.PATH,
                            example = "123"
                    ),
                    @Parameter(
                            name = "productsId",
                            description = "Lista de IDs dos produtos a serem adicionados ao catálogo",
                            required = true,
                            in = ParameterIn.QUERY,
                            example = "1,2,3"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Produtos adicionados com sucesso ao catálogo",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddProductToCatalogDTO.Response.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Catálogo ou algum produto não encontrado"
                    )
            }
    )
    public Mono<AddProductToCatalogDTO.Response> addProduct(@PathVariable String catalogId, @RequestParam List<String> productsId);

    @Operation(
            summary = "Remover um produto do catálogo",
            tags = {"Catalog API"},
            operationId = "removeProduct",
            description = "Remove um produto específico do catálogo com base no catalogId e productId fornecidos",
            parameters = {
                   // @Parameter(description = "ID do catálogo de onde o produto será removido", required = true),
                   // @Parameter(description = "ID do produto a ser removido do catálogo", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Produto removido com sucesso do catálogo",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RemoveProductFromCatalogDTO.Response.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Catálogo ou produto não encontrado"
                    )
            }
    )
    public Mono<RemoveProductFromCatalogDTO.Response> removeProduct(String catalogId, String productId);
}