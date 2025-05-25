package com.app.example.order.application.ppi;

import com.app.example.order.domain.dto.*;
import reactor.core.publisher.Mono;

/**
 * Porta de saída (interface de driver) para operações relacionadas a pedidos.
 * <p>
 * Define o contrato que deve ser implementado por adaptadores para lidar com as operações
 * de criação, cancelamento, marcação de pagamento e expedição de pedidos.
 * <p>
 * Essa interface permite a separação entre a lógica de negócio (casos de uso) e as implementações
 * específicas de persistência ou comunicação.
 *
 * Essa abordagem está alinhada aos princípios da Arquitetura Hexagonal (Ports & Adapters).
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public interface OrderPort {

    /**
     * Cria um novo pedido com base nos dados fornecidos.
     *
     * @param request Objeto contendo as informações do pedido a ser criado.
     * @return Um {@link Mono} contendo a resposta da operação de criação do pedido.
     */
    Mono<CreateOrderDTO.Response> createOrder(CreateOrderDTO.Request request);

    /**
     * Cancela um pedido existente com base no seu identificador.
     *
     * @param orderId Identificador do pedido a ser cancelado.
     * @return Um {@link Mono} contendo a resposta da operação de cancelamento.
     */
    Mono<CancelOrderDTO.Response> cancelOrder(String orderId);

    /**
     * Marca um pedido como pago com base no seu identificador.
     *
     * @param orderId Identificador do pedido a ser marcado como pago.
     * @return Um {@link Mono} contendo a resposta da operação de marcação de pagamento.
     */
    Mono<MarkOrderAsPaidDTO.Response> markOrderAsPaid(String orderId);

    /**
     * Expede um pedido com base no seu identificador.
     *
     * @param orderId Identificador do pedido a ser expedido.
     * @return Um {@link Mono} contendo a resposta da operação de expedição do pedido.
     */
    Mono<ShipOrderDTO.Response> shipOrder(String orderId);

    /**
     * Recupera um pedido pelo seu identificador.
     *
     * @param request Objeto contendo o identificador do pedido a ser recuperado.
     * @return Um {@link Mono} contendo os dados do pedido, se encontrado.
     */
    Mono<FindByIdDTO.Response> findById(FindByIdDTO.Request request);
}