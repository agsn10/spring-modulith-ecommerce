package com.app.example.order.api.openapi;

import com.app.example.order.domain.dto.CancelOrderDTO;
import com.app.example.order.domain.dto.CreateOrderDTO;
import com.app.example.order.domain.dto.MarkOrderAsPaidDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@Tag(
        name = "Pedido API",
        description = "Operações relacionadas a pedidos"
)
public interface OrderOpenApi {

    @Operation(
            summary = "Criar um novo pedido",
            description = "Cria um novo pedido com os dados fornecidos",
            operationId = "createOrder",
            tags = {"Pedido API"},
            requestBody = @RequestBody(
                    description = "Dados necessários para criar um pedido",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateOrderDTO.Request.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Pedido criado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreateOrderDTO.Response.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados inválidos para criação de pedido"
                    )
            }
    )
    Mono<CreateOrderDTO.Response> create(CreateOrderDTO.Request request);

    @Operation(
            summary = "Cancelar um pedido",
            description = "Cancela um pedido com base no ID fornecido",
            operationId = "cancelOrder",
            tags = {"Pedido API"},
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID do pedido a ser cancelado",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pedido cancelado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CancelOrderDTO.Response.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pedido não encontrado"
                    )
            }
    )
    Mono<CancelOrderDTO.Response> cancel(String id);

    @Operation(
            summary = "Marcar pedido como pago",
            description = "Marca um pedido como pago com base no ID fornecido",
            operationId = "markOrderAsPaid",
            tags = {"Pedido API"},
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID do pedido a ser marcado como pago",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pedido marcado como pago com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MarkOrderAsPaidDTO.Response.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pedido não encontrado"
                    )
            }
    )
    Mono<MarkOrderAsPaidDTO.Response> markAsPaid(String id);
}