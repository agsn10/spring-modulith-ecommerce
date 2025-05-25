package com.app.example.order.mapper;

import com.app.example.order.application.command.CreateOrderCommand;
import com.app.example.order.domain.dto.CreateOrderDTO;
import org.mapstruct.Mapper;

/**
 * Mapper responsável por converter entre objetos de transporte de dados (DTO)
 * e objetos de comando usados na lógica de criação de pedidos.
 * <p>
 * Este componente utiliza o MapStruct para gerar automaticamente o código de mapeamento
 * entre {@link CreateOrderDTO.Request} e {@link CreateOrderCommand.Input}, bem como
 * entre {@link CreateOrderCommand.Output} e {@link CreateOrderDTO.Response}.
 * <p>
 * O uso desse mapper promove uma separação clara entre a camada de transporte
 * (API) e a camada de aplicação (casos de uso), facilitando a manutenção
 * e evolução da aplicação.
 *
 * <p><b>Exemplo de uso:</b></p>
 * <pre>{@code
 * CreateOrderCommand.Input input = createOrderMapper.toInput(requestDto);
 * CreateOrderDTO.Response responseDto = createOrderMapper.toResponse(output);
 * }</pre>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Mapper(componentModel = "spring")
public interface CreateOrderMapper {

    /**
     * Converte um DTO de requisição de criação de pedido para o comando de entrada.
     *
     * @param request DTO contendo os dados da requisição.
     * @return Objeto de entrada do comando {@link CreateOrderCommand.Input}.
     */
    CreateOrderCommand.Input toInput(CreateOrderDTO.Request request);

    /**
     * Converte a saída do comando de criação de pedido para o DTO de resposta.
     *
     * @param output Resultado da execução do comando.
     * @return DTO contendo os dados de resposta da criação de pedido.
     */
    CreateOrderDTO.Response toResponse(CreateOrderCommand.Output output);
}