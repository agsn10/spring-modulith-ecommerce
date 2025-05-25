package com.app.example.invoice.application.ppi;

import com.app.example.invoice.domain.dto.GenerateInvoiceDTO;
import reactor.core.publisher.Mono;

/**
 * Interface que define o contrato para a comunicação com o sistema de de faturas (Invoice).
 * <p>
 * Esta é uma Interface de entrada (Primary Port Interface - PPI) para operações relacionadas à faturas.
 * Ela faz parte da arquitetura hexagonal, facilitando a comunicação entre o domínio e os casos de uso.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public interface InvoicePort {

    /**
     * Método para gerar uma fatura com base nos dados fornecidos.
     *
     * @param request Dados necessários para a geração da fatura, incluindo o identificador do pedido e o valor total.
     * @return Um {@link Mono} que contém a resposta com as informações da fatura gerada, como o ID da fatura,
     *         número da fatura e data de geração.
     *
     * @see GenerateInvoiceDTO.Request
     * @see GenerateInvoiceDTO.Response
     */
    Mono<GenerateInvoiceDTO.Response> generateInvoice(GenerateInvoiceDTO.Request request);
}
