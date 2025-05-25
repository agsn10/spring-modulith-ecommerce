package com.app.example.order.mapper;

import com.app.example.order.application.command.MarkOrderAsPaidCommand;
import com.app.example.order.domain.dto.MarkOrderAsPaidDTO;
import org.mapstruct.Mapper;

/**
 * Mapper responsável pela conversão entre os objetos usados no transporte de dados (DTO)
 * e os objetos de comando relacionados ao pagamento de pedidos.
 * <p>
 * Este componente realiza o mapeamento entre o identificador da ordem
 * recebido como String e o {@link MarkOrderAsPaidCommand.Input}, bem como do {@link MarkOrderAsPaidCommand.Output}
 * para o {@link MarkOrderAsPaidDTO.Response}.
 * <p>
 * Promove a separação de preocupações entre a camada de API e a lógica de aplicação.
 *
 * <p><b>Exemplo de uso:</b></p>
 * <pre>{@code
 * MarkOrderAsPaidCommand.Input input = markOrderAsPaidMapper.toInput(orderId);
 * MarkOrderAsPaidDTO.Response response = markOrderAsPaidMapper.toResponse(output);
 * }</pre>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Mapper(componentModel = "spring")
public interface MarkOrderAsPaidMapper {

    /**
     * Converte um ID de pedido para o objeto de entrada do comando de marcação de pagamento.
     *
     * @param orderId Identificador do pedido.
     * @return Objeto de entrada {@link MarkOrderAsPaidCommand.Input}.
     */
    default MarkOrderAsPaidCommand.Input toInput(String orderId) {
        return new MarkOrderAsPaidCommand.Input(orderId);
    }

    /**
     * Converte o resultado da operação de marcação de pagamento para um DTO de resposta.
     *
     * @param output Objeto de saída do comando.
     * @return DTO de resposta {@link MarkOrderAsPaidDTO.Response}.
     */
    MarkOrderAsPaidDTO.Response toResponse(MarkOrderAsPaidCommand.Output output);
}