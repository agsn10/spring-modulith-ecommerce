package com.app.example.payment.api.resource;

import com.app.example.payment.api.openapi.PaymentOpenapi;
import com.app.example.payment.application.ppi.PaymentPort;
import com.app.example.payment.domain.dto.ProcessPaymentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controlador REST responsável por expor os endpoints da API de pagamento.
 *
 * <p>
 * Essa classe faz parte da camada de entrada da aplicação (API) e delega
 * a lógica de negócio à porta {@link PaymentPort}, que é responsável pelo
 * processamento de pagamentos.
 * </p>
 *
 * <p>
 * Os endpoints seguem o padrão REST e utilizam a biblioteca Reactor (WebFlux)
 * para programação reativa.
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentResource implements PaymentOpenapi {

    private final PaymentPort paymentPort;

    /**
     * Endpoint responsável por processar o pagamento de um pedido.
     *
     * @param orderId identificador do pedido
     * @param method  método de pagamento a ser utilizado (ex: "pix", "boleto", "credito")
     * @return um {@link Mono} contendo a resposta do processamento do pagamento
     */
    @PostMapping("/{orderId}/process")
    public Mono<ProcessPaymentDTO.Response> process(@PathVariable String orderId, @RequestParam String method) {
        return paymentPort.processPayment(new ProcessPaymentDTO.Request(orderId, method));
    }
}
