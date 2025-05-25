package com.app.example.payment.mapper;

import com.app.example.payment.application.command.ProcessPaymentCommand;
import com.app.example.payment.domain.dto.ProcessPaymentDTO;
import com.app.example.payment.domain.po.PaymentPO;
import com.app.example.payment.mapper.helper.PaymentMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper responsável por converter objetos entre as camadas de DTO, Command e Entidades persistidas relacionadas a pagamento.
 *
 * <p>Utiliza MapStruct para geração automática de código de mapeamento.</p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Mapper(componentModel = "spring", uses = {PaymentMapperHelper.class})
public interface ProcessPaymentMapper {

    /**
     * Converte um {@link ProcessPaymentCommand.Output} em um {@link ProcessPaymentDTO.Response}.
     *
     * @param output o resultado da execução do caso de uso de pagamento
     * @return uma resposta formatada para o consumidor da API
     */
    ProcessPaymentDTO.Response toResponse(ProcessPaymentCommand.Output output);

    /**
     * Converte um {@link ProcessPaymentDTO.Request} em um {@link ProcessPaymentCommand.Input}.
     *
     * @param request o payload da requisição de pagamento recebido via API
     * @return comando de entrada utilizado pelo caso de uso
     */
    ProcessPaymentCommand.Input toInput(ProcessPaymentDTO.Request request);

    /**
     * Converte um {@link PaymentPO} (entidade persistida) em um {@link ProcessPaymentCommand.Output},
     * atribuindo uma mensagem padrão de sucesso.
     *
     * @param paymentPO entidade persistida de pagamento
     * @return resultado da operação de pagamento com mensagem de sucesso
     */
    @Mapping(target = "message", expression = "java(\"Pagamento realizado com sucesso\")")
    ProcessPaymentCommand.Output fromPoToOutput(PaymentPO paymentPO);

}