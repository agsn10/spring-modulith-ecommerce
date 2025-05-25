package com.app.example.order.mapper;

import com.app.example.order.application.command.ShipOrderCommand;
import com.app.example.order.domain.dto.ShipOrderDTO;
import org.mapstruct.Mapper;

/**
 * Mapper responsável pela conversão entre os objetos usados no transporte de dados (DTO)
 * e os objetos de comando relacionados ao envio de pedidos.
 * <p>
 * Este componente realiza o mapeamento entre o identificador da ordem
 * recebido como String e o {@link ShipOrderCommand.Input}, bem como do {@link ShipOrderCommand.Output}
 * para o {@link ShipOrderDTO.Response}.
 * <p>
 * Promove a separação de preocupações entre a camada de API e a lógica de aplicação.
 *
 * <p><b>Exemplo de uso:</b></p>
 * <pre>{@code
 * ShipOrderCommand.Input input = shipOrderMapper.toInput(orderId);
 * ShipOrderDTO.Response response = shipOrderMapper.toResponse(output);
 * }</pre>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Mapper(componentModel = "spring")
public interface ShipOrderMapper {

    /**
     * Converte um ID de pedido para o objeto de entrada do comando de envio de pedido.
     *
     * @param orderId Identificador do pedido.
     * @return Objeto de entrada {@link ShipOrderCommand.Input}.
     */
    default ShipOrderCommand.Input toInput(String orderId) {
        return new ShipOrderCommand.Input(orderId);
    }

    /**
     * Converte o resultado da operação de envio de pedido para um DTO de resposta.
     *
     * @param output Objeto de saída do comando.
     * @return DTO de resposta {@link ShipOrderDTO.Response}.
     */
    ShipOrderDTO.Response toResponse(ShipOrderCommand.Output output);
}