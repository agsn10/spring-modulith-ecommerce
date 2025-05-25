package com.app.example.order.application.usecase;

import com.app.example.order.application.command.CreateOrderCommand;
import com.app.example.order.domain.enums.OrderStatusEnum;
import com.app.example.order.domain.event.OrderCreated;
import com.app.example.order.domain.po.OrderItemPO;
import com.app.example.order.domain.po.OrderPO;
import com.app.example.order.infra.repository.OrderItemRepository;
import com.app.example.order.infra.repository.OrderRepository;
import com.app.example.product.application.ppi.ProductPort;
import com.app.example.product.domain.dto.ChangeStockDTO;
import com.app.example.product.domain.dto.GetStockDTO;
import com.app.example.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Caso de uso responsável pela criação de um pedido.
 * <p>
 * Este caso de uso define a lógica de negócios para criar um pedido no sistema.
 * Ele implementa a interface {@link IUseCase} e é responsável por processar os dados de entrada
 * e retornar o resultado correspondente após a execução da lógica de criação de pedidos.
 * <p>
 * O método {@link #execute(CreateOrderCommand.Input)} é responsável por realizar a execução do processo
 * de criação de um pedido, retornando a saída necessária para o fluxo do sistema.
 *
 * <p><b>Exemplo de uso:</b></p>
 * <pre>{@code
 * CreateOrderUseCase createOrderUseCase = applicationContext.getBean(CreateOrderUseCase.class);
 * Mono<CreateOrderCommand.Output> result = createOrderUseCase.execute(input);
 * }</pre>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("createOrderUseCase")
public class CreateOrderUseCase implements IUseCase<CreateOrderCommand.Input, Mono<CreateOrderCommand.Output>> {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductPort productPort;
    private final ApplicationEventPublisher publisher;

    /**
     * Executa o caso de uso de criação de pedido.
     * <p>
     * Este método recebe os dados de entrada para a criação de um pedido e executa a lógica
     * de validação e persistência do pedido.
     * Após a confirmação da criação do pedido, o estoque de cada produto incluído no pedido
     * será atualizado com a respectiva baixa.
     * </p>
     *
     * <p>
     * <strong>Fluxo geral:</strong><br>
     * Cliente cria um pedido contendo, por exemplo, 2 unidades do produto "X".
     * <ul>
     *   <li>Valida o pedido</li>
     *   <li>Calcula o valor do pedido</li>
     *   <li>Salva o pedido</li>
     *   <li>Para cada item do pedido:
     *     <ul>
     *       <li>Chama <code>ChangeStock</code> com os parâmetros (productId, -quantidadeVendida)</li>
     *     </ul>
     *   </li>
     * </ul>
     * </p>
     *
     * @param input Dados de entrada necessários para criar o pedido, como informações sobre os itens e cliente.
     * @return Retorna um {@link reactor.core.publisher.Mono} com a saída do comando de criação do pedido.
     */

    @Override
    public Mono<CreateOrderCommand.Output> execute(CreateOrderCommand.Input input) {
        log.info("Iniciando execução do comando de criação de pedido para o cliente {}", input.clientId());
        // 1. Validar estoque dos produtos
        return Flux.fromIterable(input.products())
                .doOnNext(product -> log.info("Validando estoque para produto {}", product.productId()))
                .flatMap(product ->
                        productPort.getStock(new GetStockDTO.Request(product.productId().toString()))
                                .flatMap(stock -> {
                                    if (stock.quantity() <= 0) {
                                        log.warn("Produto {} sem estoque", stock.productId());
                                        return Mono.error(new IllegalStateException("Produto " + stock.productId() + " sem estoque"));
                                    }
                                    log.info("Produto {} com estoque disponível: {}", stock.productId(), stock.quantity());

                                    // Mantém a quantidade solicitada original
                                    return Mono.just(new ValidatedProduct(
                                            stock.productId(),
                                            stock.name(),
                                            stock.description(),
                                            stock.price(),
                                            product.quantity()  // <- preserva quantidade informada no input
                                    ));
                                })
                )
                .collectList()
                .flatMap(validProducts -> {

                    log.info("Todos os produtos validados. Quantidade: {}", validProducts.size());
                    // 2. Calcular o valor total do pedido
                    BigDecimal totalAmount = validProducts.stream()
                            .map(p -> p.price().multiply(BigDecimal.valueOf(p.quantitySolicited())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    log.info("Valor total do pedido calculado: {}", totalAmount);
                    // 3. Criar e persistir o pedido
                    OrderPO orderPO = new OrderPO();
                    orderPO.setClientId(input.clientId());
                    orderPO.setTotalAmount(totalAmount);
                    orderPO.setCreatedAt(LocalDateTime.now());
                    orderPO.setStatus(OrderStatusEnum.PENDING.name());
                    return orderRepository.save(orderPO)
                            .doOnNext(savedOrder -> log.info("Pedido salvo com ID: {}", savedOrder.getId()))
                            .flatMap(savedOrder -> {
                                // 4. Salvar os itens do pedido relacionados ao pedido salvo
                                List<OrderItemPO> itemEntities = validProducts.stream()
                                        .map(p -> {
                                            OrderItemPO item = new OrderItemPO();
                                            item.setProductName(p.name());
                                            item.setOrderId(savedOrder.getId());
                                            item.setProductId(UUID.fromString(p.productId()));
                                            item.setQuantity(p.quantitySolicited);
                                            item.setUnitPrice(p.price());
                                            item.setTotalPrice(p.price().multiply(BigDecimal.valueOf(p.quantitySolicited)));
                                            return item;
                                        })
                                        .toList();
                                log.info("Salvando {} itens do pedido", itemEntities.size());
                                return orderItemRepository.saveAll(itemEntities)
                                        .doOnNext(item -> log.info("Item salvo: produto {}, quantidade {}", item.getProductId(), item.getQuantity()))
                                        .flatMap(item ->
                                                // 5. Atualiza o estoque de cada item do pedido relacionados ao pedido salvo
                                                productPort.changeStock(new ChangeStockDTO.Request(item.getProductId().toString(), item.getQuantity()))
                                                        .doOnNext(resp -> log.info("Estoque atualizado para produto {}", item.getProductId()))
                                        )
                                        .then(Mono.fromRunnable(() -> {
                                                    // Envia o evento após criação do pedido
                                                    OrderCreated event = new OrderCreated(
                                                            savedOrder.getClientId(),
                                                            savedOrder.getId(),
                                                            savedOrder.getTotalAmount()
                                                    );
                                                    publisher.publishEvent(event);
                                                    log.info("Evento OrderCreated publicado para o pedido {}", savedOrder.getId());
                                                })
                                                .thenReturn(new CreateOrderCommand.Output(
                                                        savedOrder.getId(),
                                                        savedOrder.getClientId(),
                                                        savedOrder.getTotalAmount(),
                                                        savedOrder.getStatus(),
                                                        savedOrder.getCreatedAt()
                                                )));

                            });
                });
    }

    private record ValidatedProduct(
            String productId,
            String name,
            String description,
            BigDecimal price,
            int quantitySolicited
    ) {}

}