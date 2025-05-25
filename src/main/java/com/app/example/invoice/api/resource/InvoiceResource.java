package com.app.example.invoice.api.resource;

import com.app.example.invoice.application.ppi.InvoicePort;
import com.app.example.invoice.domain.dto.GenerateInvoiceDTO;
import com.app.example.invoice.domain.po.InvoicePO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para geração de faturas a partir de pedidos já existentes.
 */
@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceResource {

    private final InvoicePort invoicePort;

    /**
     * Gera uma fatura para o pedido com o ID informado.
     * Internamente, o serviço:
     * 1. Busca o pedido ({@code orderId}) no repositório.
     * 2. Constrói o objeto {@link InvoicePO}.
     * 3. Persiste a fatura no banco.
     * 4. Publica o evento {@code InvoiceGenerated} após commit.
     *
     * @param orderId ID do pedido para o qual será gerada a fatura.
     * @return {@link Mono} com a fatura recém-criada.
     */
    @PostMapping("/generate/{orderId}")
    public Mono<GenerateInvoiceDTO.Response> generate(@PathVariable String orderId) {
        return invoicePort.generateInvoice(new GenerateInvoiceDTO.Request(orderId));
    }
}
