package com.app.example.payment.application.ppi;

import com.app.example.payment.domain.dto.GetPaymentDTO;
import com.app.example.payment.domain.dto.ProcessPaymentDTO;
import reactor.core.publisher.Mono;

/**
 * Porta de entrada para as operações de pagamento, seguindo o padrão
 * Hexagonal (Ports & Adapters).
 *
 * Esta interface define os contratos disponíveis para processar um pagamento
 * em nome de um pedido.
 *
 * <p>
 * Implementações desta interface são responsáveis por encapsular a lógica
 * de negócio referente ao processamento de pagamentos.
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public interface PaymentPort {


    /**
     * Processa o pagamento para um pedido específico com o método informado.
     * <p>
     * Esta operação representa a etapa em que o cliente escolhe um método
     * de pagamento e o sistema realiza o processamento correspondente,
     * podendo incluir validações, chamadas a gateways de pagamento,
     * registro de transações, entre outros.
     * </p>
     *
     * @param request objeto contendo o ID do pedido e o método de pagamento
     * @return um {@link Mono} com os dados da resposta do processamento, incluindo o ID do pagamento e uma mensagem de status
     */
    Mono<ProcessPaymentDTO.Response> processPayment(ProcessPaymentDTO.Request request);

    /**
     * Obtém os detalhes de um pagamento com base na solicitação de consulta fornecida.
     *
     * <p>Este método aceita um objeto de requisição {@link GetPaymentDTO.Request} contendo os dados necessários
     * para localizar o pagamento. Ele utiliza o caso de uso de obtenção de pagamento para buscar as informações
     * e retorna os dados de pagamento no formato de resposta {@link GetPaymentDTO.Response}.</p>
     *
     * @param request objeto de requisição contendo os dados para buscar o pagamento (por exemplo, ID do pedido)
     * @return um {@link Mono<GetPaymentDTO.Response>} contendo os dados de pagamento ou uma falha reativa se não encontrado
     */
    Mono<GetPaymentDTO.Response> getPaymentByOrderId(GetPaymentDTO.Request request);

    /**
     * <b>Método atualmente desativado.</b>
     * <p>
     * A confirmação explícita de pagamentos foi descartada, pois o sistema assume
     * que o processo de pagamento é síncrono e o resultado (confirmado ou não)
     * já é conhecido imediatamente após o processamento.
     * </p>
     * <p>
     * Este método poderá ser reativado em uma futura evolução para uma
     * arquitetura de microsserviços com suporte a eventos assíncronos.
     * </p>
     *
     * @param paymentId identificador do pagamento
     * @return um {@link Mono} com a resposta da confirmação
     */
    // Mono<ConfirmPaymentDTO.Response> confirmPayment(String paymentId);
}