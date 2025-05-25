package com.app.example.invoice.application.usecase.helper;

import com.app.example.invoice.application.command.GenerateInvoiceCommand;
import com.app.example.invoice.domain.po.InvoicePO;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

/**
 * Classe utilitária responsável por gerar faturas a partir de comandos de entrada.
 * Contém métodos para construir objetos {@link InvoicePO} com base nos dados fornecidos.
 *
 * A classe é marcada como final e impede a criação de instâncias.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public class GenerateInvoiceHelper {

    /**
     * Constrói um objeto {@link InvoicePO} a partir de um comando de entrada {@link GenerateInvoiceCommand.Input}.
     *
     * @param cmd Comando de entrada contendo informações necessárias para gerar a fatura.
     * @return Uma nova instância de {@link InvoicePO} preenchida com os dados fornecidos.
     */
    public static InvoicePO buildInvoiceFrom(BigDecimal totalAmount, String orderId) {
        InvoicePO invoice = new InvoicePO();
        invoice.setOrderId(UUID.fromString(orderId));
        invoice.setInvoiceNumber("INV-" + System.currentTimeMillis());
        invoice.setGeneratedAt(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));
        invoice.setTotalAmount(totalAmount);
        return invoice;
    }
}
