package com.app.example.order.mapper;

import com.app.example.order.application.command.CancelOrderCommand;
import com.app.example.order.domain.dto.CancelOrderDTO;
import com.app.example.order.domain.po.OrderPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper responsável pela conversão entre os objetos usados no transporte de dados (DTO)
 * e os objetos de comando relacionados ao cancelamento de pedidos.
 * <p>
 * Este componente realiza o mapeamento entre o identificador da ordem
 * recebido como String e o {@link CancelOrderCommand.Input}, bem como do {@link CancelOrderCommand.Output}
 * para o {@link CancelOrderDTO.Response}.
 * <p>
 * Promove a separação de preocupações entre a camada de API e a lógica de aplicação.
 *
 * <p><b>Exemplo de uso:</b></p>
 * <pre>{@code
 * CancelOrderCommand.Input input = cancelOrderMapper.toInput(orderId);
 * CancelOrderDTO.Response response = cancelOrderMapper.toResponse(output);
 * }</pre>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Mapper(componentModel = "spring")
public interface CancelOrderMapper {

    /**
     * Converte um ID de pedido para o objeto de entrada do comando de cancelamento.
     *
     * @param orderId Identificador do pedido.
     * @return Objeto de entrada {@link CancelOrderCommand.Input}.
     */
    default CancelOrderCommand.Input toInput(String orderId) {
        return new CancelOrderCommand.Input(orderId);
    }

    /**
     * Converte o resultado da operação de cancelamento para um DTO de resposta.
     *
     * @param output Objeto de saída do comando.
     * @return DTO de resposta {@link CancelOrderDTO.Response}.
     */
    CancelOrderDTO.Response toResponse(CancelOrderCommand.Output output);
}

