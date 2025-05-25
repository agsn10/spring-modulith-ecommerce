package com.app.example.payment.domain.event;

/**
 * Evento de domínio que representa a confirmação de um pagamento.
 *
 * <p>
 * Esse evento pode ser publicado para notificar outras partes do sistema de que
 * um pagamento foi confirmado com sucesso. É útil em arquiteturas orientadas a eventos,
 * como em microsserviços ou sistemas reativos.
 * </p>
 *
 * @param paymentId Identificador único do pagamento confirmado.
 *
 * @author
 *     <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public record PaymentConfirmed(String paymentId) {
}
