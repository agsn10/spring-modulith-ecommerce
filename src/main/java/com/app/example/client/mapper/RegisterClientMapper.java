package com.app.example.client.mapper;

import com.app.example.client.application.command.RegisterClientCommand;
import com.app.example.client.domain.dto.RegisterClientDTO;
import com.app.example.client.domain.po.AddressPO;
import com.app.example.client.domain.po.ClientPO;
import com.app.example.client.mapper.helper.ClientMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 * Mapper responsável pela conversão entre os objetos de transporte de dados (DTO)
 * e os comandos relacionados ao registro de cliente.
 * <p>
 * Este componente realiza o mapeamento entre os dados recebidos na requisição
 * {@link RegisterClientDTO.Request} e o comando de entrada {@link RegisterClientCommand.Input},
 * bem como do {@link RegisterClientCommand.Output} para o {@link RegisterClientDTO.Response}.
 *
 * <p><b>Exemplo de uso:</b></p>
 * <pre>{@code
 * RegisterClientCommand.Input input = registerClientMapper.toInput(request);
 * RegisterClientDTO.Response response = registerClientMapper.toResponse(output);
 * }</pre>
 *
 * Além disso, também realiza a conversão entre os comandos e a entidade de persistência {@link ClientPO}.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Mapper(componentModel = "spring", uses = {ClientMapperHelper.class})
public interface RegisterClientMapper {

    /**
     * Converte os dados da requisição em um comando de entrada para registrar um cliente.
     *
     * @param request Dados recebidos na requisição.
     * @return Objeto de entrada {@link RegisterClientCommand.Input}.
     */
    RegisterClientCommand.Input toInput(RegisterClientDTO.Request request);

    /**
     * Converte a saída do comando em um DTO de resposta.
     *
     * @param output Resultado da operação de registro.
     * @return DTO de resposta {@link RegisterClientDTO.Response}.
     */
    RegisterClientDTO.Response toResponse(RegisterClientCommand.Output output);

    /**
     * Converte o comando de entrada em uma entidade de persistência {@link ClientPO}.
     * <p>
     * Este método é utilizado para persistir os dados recebidos em uma base relacional ou similar.
     *
     * @param input Objeto de entrada contendo os dados do cliente.
     * @return Instância de {@link ClientPO} pronta para ser salva.
     */
    @Mappings(value = {
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "email", target = "email"),
        @Mapping(source = "phone", target = "phone")
    })
    ClientPO toClientPO(RegisterClientCommand.Input input);

    @Mappings(value = {
        @Mapping(source = "rua", target = "rua"),
        @Mapping(source = "numero", target = "numero"),
        @Mapping(source = "cidade", target = "cidade"),
        @Mapping(source = "estado", target = "estado"),
        @Mapping(source = "cep", target = "cep")
    })
    AddressPO toAddressPO(RegisterClientCommand.Input input);

    /**
     * Converte a entidade {@link ClientPO} persistida em um comando de saída {@link RegisterClientCommand.Output}.
     * <p>
     * Além de carregar o identificador do cliente, também adiciona uma mensagem padrão de sucesso.
     *
     * @param clientPO Entidade do cliente que foi salva.
     * @return Comando de saída com dados do cliente e mensagem.
     */
    @Mapping(target = "message", expression = "java(\"Cliente cadastrado com sucesso\")")
    @Mapping(source = "id", target = "clientId")
    RegisterClientCommand.Output toOutput(ClientPO clientPO);
}