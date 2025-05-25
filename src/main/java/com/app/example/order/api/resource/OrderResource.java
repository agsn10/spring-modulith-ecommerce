package com.app.example.order.api.resource;

import com.app.example.order.api.openapi.OrderOpenApi;
import com.app.example.order.application.ppi.OrderPort;
import com.app.example.order.domain.dto.CancelOrderDTO;
import com.app.example.order.domain.dto.CreateOrderDTO;
import com.app.example.order.domain.dto.MarkOrderAsPaidDTO;
import com.app.example.order.domain.dto.ShipOrderDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para operações relacionadas a pedidos (orders).
 * <p>
 * Expõe endpoints para criação, cancelamento, pagamento e envio de pedidos.
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderResource implements OrderOpenApi {

    private final OrderPort orderPort;

    /**
     * Cria um novo pedido com os dados fornecidos.
     *
     * @param request dados necessários para a criação do pedido
     * @return resposta contendo as informações do pedido criado
     */
    @PostMapping
    public Mono<CreateOrderDTO.Response> create(@Valid @RequestBody CreateOrderDTO.Request request) {
        return orderPort.createOrder(request);
    }

    /**
     * Cancela um pedido existente com base no ID fornecido.
     *
     * @param id identificador único do pedido a ser cancelado
     * @return Mono vazio indicando a conclusão da operação
     */
    @PostMapping("/{id}/cancel")
    public Mono<CancelOrderDTO.Response> cancel(@PathVariable String id) {
        return orderPort.cancelOrder(id);
    }

    /**
     * Marca um pedido como pago com base no ID fornecido.
     *
     * @param id identificador único do pedido
     * @return Mono vazio indicando a conclusão da operação
     */
    @PatchMapping("/{id}/pay")
    public Mono<MarkOrderAsPaidDTO.Response> markAsPaid(@PathVariable String id) {
        return orderPort.markOrderAsPaid(id);
    }

    /**
     * Marca um pedido como enviado com base no ID fornecido.
     *
     * @param id identificador único do pedido
     * @return Mono vazio indicando a conclusão da operação
     */
    @PatchMapping("/{id}/ship")
    public Mono<ShipOrderDTO.Response> ship(@PathVariable String id) {
        return orderPort.shipOrder(id);
    }
}
