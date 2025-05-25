package com.app.example.client.application.command;

import java.io.Serializable;
import java.util.UUID;

/**
 * Comando de entrada e saída para o caso de uso de registro de cliente.
 * <p>
 * Define os dados de entrada ({@link Input}) e os dados de saída ({@link Output})
 * utilizados pela lógica de negócio para processar o registro de um novo cliente.
 * <p>
 * Essa classe segue o padrão Command aplicado na arquitetura orientada a casos de uso.
 * Pode ser utilizada como contrato entre as camadas de aplicação e de domínio.
 *
 * @author Antonio Neto
 */
public sealed interface RegisterClientCommand extends Serializable
        permits RegisterClientCommand.Input, RegisterClientCommand.Output {

    /**
     * Dados de entrada para o registro de um novo cliente.
     *
     * @param name   Nome completo do cliente.
     * @param email  Endereço de e-mail do cliente.
     * @param phone  Número de telefone do cliente.
     * @param rua    Nome da rua do endereço do cliente.
     * @param numero Número da residência do cliente.
     * @param cidade Cidade do cliente.
     * @param estado Estado do cliente.
     * @param cep    Código postal do endereço do cliente.
     */
    record Input(
            String name,
            String email,
            String phone,
            String rua,
            String numero,
            String cidade,
            String estado,
            String cep
    ) implements RegisterClientCommand {}

    /**
     * Resultado da operação de registro de cliente.
     *
     * @param clientId Identificador único do cliente criado.
     * @param message  Mensagem de confirmação do registro.
     */
    record Output(
            UUID clientId,
            String message
    ) implements RegisterClientCommand {}
}
