package com.app.example.client.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO para registro de cliente.
 * <p>
 * Define os dados de entrada (request) e os dados de saída (response)
 * relacionados à operação de registro de um novo cliente.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Schema(description = "DTO para registro de cliente")
public sealed interface RegisterClientDTO extends Serializable
        permits RegisterClientDTO.Request, RegisterClientDTO.Response {

    /**
     * Dados de requisição para registrar um novo cliente.
     */
    @Schema(description = "Dados necessários para o registro de um novo cliente")
    record Request(
            @NotBlank(message = "{registerClient.request.name.notBlank}")
            @Schema(description = "Nome completo do cliente", example = "João da Silva")
            String name,

            @NotBlank(message = "{registerClient.request.email.notBlank}")
            @Email(message = "{registerClient.request.email.valid}")
            @Schema(description = "Endereço de e-mail do cliente", example = "joao.silva@email.com")
            String email,

            @Schema(description = "Número de telefone do cliente", example = "+55 48 99999-8888")
            String phone,

            @NotBlank(message = "{registerClient.request.rua.notBlank}")
            @Schema(description = "Rua do endereço", example = "Rua das Flores")
            String rua,

            @NotBlank(message = "{registerClient.request.numero.notBlank}")
            @Schema(description = "Número da residência", example = "123")
            String numero,

            @NotBlank(message = "{registerClient.request.cidade.notBlank}")
            @Schema(description = "Cidade do cliente", example = "Florianópolis")
            String cidade,

            @NotBlank(message = "{registerClient.request.estado.notBlank}")
            @Schema(description = "Estado do cliente", example = "SC")
            String estado,

            @NotBlank(message = "{registerClient.request.cep.notBlank}")
            @Pattern(regexp = "\\d{5}-\\d{3}", message = "{registerClient.request.cep.pattern}")
            @Schema(description = "CEP do endereço", example = "88000-000")
            String cep
    ) implements RegisterClientDTO {}

    /**
     * Dados de resposta após o registro de um cliente.
     */
    @Schema(description = "Resposta da operação de registro de cliente")
    record Response(
            @Schema(description = "Identificador único do cliente criado", example = "123e4567-e89b-12d3-a456-426614174000")
            UUID clientId,

            @Schema(description = "Mensagem de confirmação do registro", example = "Cliente registrado com sucesso")
            String message
    ) implements RegisterClientDTO {}
}
