package com.app.example.order.application.ppi;

import com.app.example.order.application.command.*;
import com.app.example.order.domain.dto.*;
import com.app.example.order.mapper.*;
import com.app.example.shared.usecase.IUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import reactor.core.publisher.Mono;

/**
 * Configuração do PPI (Primary Port Interface) para o processamento de pedidos.
 * <p>
 * Esta classe define os mapeamentos entre os casos de uso e as interfaces de domínio para criação, cancelamento, pagamento e envio de pedidos.
 * É responsável por fornecer a implementação da interface {@link OrderPort}, utilizando as dependências de casos de uso e mappers.
 *
 * <p><b>Exemplo de uso:</b></p>
 * <pre>{@code
 * OrderPort orderPort = applicationContext.getBean(OrderPort.class);
 * orderPort.createOrder(createOrderRequest);
 * }</pre>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Lazy
@Configuration
public class OrderPpiConfig {

    /**
     * Define um {@link OrderPort} com os casos de uso e mappers necessários para gerenciar as operações de pedido.
     *
     * Este método mapeia os dados de entrada do DTO para o formato de entrada de comando adequado, executa o caso de uso correspondente e mapeia
     * a saída de volta para o formato de DTO para a resposta.
     *
     * @param createOrderuseCase Caso de uso para criar um pedido.
     * @param createOrderMapper Mapper para converter entre DTO e comandos de criação de pedido.
     * @param cancelOrderUseCase Caso de uso para cancelar um pedido.
     * @param cancelOrderMapper Mapper para converter entre DTO e comandos de cancelamento de pedido.
     * @param markOrderAsPaid Caso de uso para marcar um pedido como pago.
     * @param markOrderAsPaidMapper Mapper para converter entre DTO e comandos de pagamento de pedido.
     * @param shipOrderUseCase Caso de uso para enviar um pedido.
     * @param shipOrderMapper Mapper para converter entre DTO e comandos de envio de pedido.
     * @return A implementação do {@link OrderPort} com a execução dos casos de uso.
     */
    @Bean("orderPort")
    public OrderPort orderPort(@Qualifier("createOrderUseCase") IUseCase<CreateOrderCommand.Input, Mono<CreateOrderCommand.Output>> createOrderuseCase,
                              CreateOrderMapper createOrderMapper,
                              @Qualifier("cancelOrderUseCase") IUseCase<CancelOrderCommand.Input, Mono<CancelOrderCommand.Output>> cancelOrderUseCase,
                              CancelOrderMapper cancelOrderMapper,
                              @Qualifier("markOrderAsPaidUseCase") IUseCase<MarkOrderAsPaidCommand.Input, Mono<MarkOrderAsPaidCommand.Output>> markOrderAsPaid,
                              MarkOrderAsPaidMapper markOrderAsPaidMapper,
                              @Qualifier("shipOrderUseCase") IUseCase<ShipOrderCommand.Input, Mono<ShipOrderCommand.Output>> shipOrderUseCase,
                              ShipOrderMapper shipOrderMapper,
                              @Qualifier("findByIdUseCase") IUseCase<FindByIdCommand.Input, Mono<FindByIdCommand.Output>> findByIdUseCase,
                              FindByIdMapper findByIdMapper) {

        return new OrderPort() {

            /**
             * Cria um novo pedido.
             *
             * Este método converte a requisição de criação de pedido para o formato esperado pelo caso de uso, executa a criação do pedido
             * e retorna a resposta como um DTO de criação de pedido.
             *
             * @param request DTO de requisição para criar um pedido.
             * @return Resposta do comando de criação do pedido.
             */
            @Override
            public Mono<CreateOrderDTO.Response> createOrder(CreateOrderDTO.Request request) {
                CreateOrderCommand.Input input = createOrderMapper.toInput(request);
                return createOrderuseCase.execute(input).map(createOrderMapper::toResponse);
            }

            /**
             * Cancela um pedido existente.
             *
             * Este método converte o ID do pedido para o formato de entrada do comando de cancelamento, executa o cancelamento
             * do pedido e retorna a resposta como um DTO de cancelamento de pedido.
             *
             * @param orderId Identificador único do pedido a ser cancelado.
             * @return Resposta do comando de cancelamento do pedido.
             */
            @Override
            public Mono<CancelOrderDTO.Response> cancelOrder(String orderId) {
                CancelOrderCommand.Input input = cancelOrderMapper.toInput(orderId);
                return cancelOrderUseCase.execute(input).map(cancelOrderMapper::toResponse);
            }

            /**
             * Marca um pedido como pago.
             *
             * Este método converte o ID do pedido para o formato de entrada do comando de pagamento, executa a marcação
             * do pedido como pago e retorna a resposta como um DTO de pagamento de pedido.
             *
             * @param orderId Identificador único do pedido a ser marcado como pago.
             * @return Resposta do comando de marcação de pagamento do pedido.
             */
            @Override
            public Mono<MarkOrderAsPaidDTO.Response> markOrderAsPaid(String orderId) {
                MarkOrderAsPaidCommand.Input input = markOrderAsPaidMapper.toInput(orderId);
                return markOrderAsPaid.execute(input).map(markOrderAsPaidMapper::toResponse);
            }

            /**
             * Envia um pedido.
             *
             * Este método converte o ID do pedido para o formato de entrada do comando de envio, executa o envio
             * do pedido e retorna a resposta como um DTO de envio de pedido.
             *
             * @param orderId Identificador único do pedido a ser enviado.
             * @return Resposta do comando de envio do pedido.
             */
            @Override
            public Mono<ShipOrderDTO.Response> shipOrder(String orderId) {
                ShipOrderCommand.Input input = shipOrderMapper.toInput(orderId);
                return shipOrderUseCase.execute(input).map(shipOrderMapper::toResponse);
            }

            /**
             * Recupera um pedido pelo seu identificador.
             *
             * @param request Objeto contendo o identificador do pedido a ser recuperado.
             * @return Um {@link Mono} contendo os dados do pedido, se encontrado.
             */
            @Override
            public Mono<FindByIdDTO.Response> findById(FindByIdDTO.Request request) {
                FindByIdCommand.Input input = findByIdMapper.toInput(request);
                return findByIdUseCase.execute(input).map(findByIdMapper::toResponse);
            }
        };
    }
}
