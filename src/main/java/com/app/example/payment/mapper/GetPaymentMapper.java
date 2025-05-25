package com.app.example.payment.mapper;

import com.app.example.payment.application.command.GetPaymentCommand;
import com.app.example.payment.domain.dto.GetPaymentDTO;
import com.app.example.payment.domain.po.PaymentPO;
import org.mapstruct.Mapper;

/**
 * Interface responsável pelo mapeamento entre os objetos de domínio (PO), comandos e DTOs relacionados a pagamentos.
 *
 * <p>Utiliza MapStruct para gerar as implementações de mapeamento automaticamente. Esta interface conecta as camadas de domínio
 * e aplicação, permitindo a conversão de dados entre os formatos esperados em cada camada.</p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Mapper(componentModel = "spring")
public interface GetPaymentMapper {

    /**
     * Mapeia um {@link PaymentPO} (Payment Persistence Object) para o comando de saída {@link GetPaymentCommand.Output}.
     *
     * <p>Este método converte um objeto de pagamento persistido (PaymentPO) em um objeto de comando de saída,
     * que é utilizado para enviar as informações de pagamento para as camadas superiores da aplicação.</p>
     *
     * @param paymentPO objeto de pagamento persistido a ser convertido
     * @return um {@link GetPaymentCommand.Output} contendo os dados do pagamento
     */
    GetPaymentCommand.Output fromPoToOutput(PaymentPO paymentPO);

    /**
     * Mapeia um {@link GetPaymentDTO.Request} para o comando de entrada {@link GetPaymentCommand.Input}.
     *
     * <p>Este método converte os dados de uma requisição de consulta de pagamento em um comando que pode ser processado
     * pela camada de serviço ou caso de uso.</p>
     *
     * @param request objeto {@link GetPaymentDTO.Request} contendo a solicitação de pagamento
     * @return um {@link GetPaymentCommand.Input} pronto para ser usado no caso de uso
     */
    GetPaymentCommand.Input toInput(GetPaymentDTO.Request request);

    /**
     * Mapeia um {@link GetPaymentCommand.Output} para a resposta {@link GetPaymentDTO.Response}.
     *
     * <p>Este método converte os dados do comando de saída de pagamento em uma resposta DTO,
     * para ser retornada ao cliente ou camada externa da aplicação.</p>
     *
     * @param output comando de saída contendo os dados do pagamento
     * @return um {@link GetPaymentDTO.Response} contendo os dados do pagamento formatados para a resposta
     */
    GetPaymentDTO.Response toResponse(GetPaymentCommand.Output output);
}
