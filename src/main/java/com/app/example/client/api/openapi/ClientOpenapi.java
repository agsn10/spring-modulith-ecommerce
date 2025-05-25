package com.app.example.client.api.openapi;

import com.app.example.client.domain.dto.RegisterClientDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@Tag(
        name = "Cliente API",
        description = "Operações relacionadas ao gerenciamento de clientes"
)
public interface ClientOpenapi {

    @Operation(
            summary = "Registrar um novo cliente",
            tags = {"Cliente API"},
            operationId = "register",
            description = "Registra um novo cliente no sistema com as informações fornecidas",
            requestBody = @RequestBody(
                    description = "Dados necessários para o registro de um novo cliente",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegisterClientDTO.Request.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Cliente registrado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegisterClientDTO.Response.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida (dados ausentes ou incorretos)"
                    )
            }
    )
    public Mono<RegisterClientDTO.Response> register(RegisterClientDTO.Request request);
}