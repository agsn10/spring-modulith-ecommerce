package com.app.example.invoice.mapper;

import com.app.example.invoice.application.command.GenerateInvoiceCommand;
import com.app.example.invoice.domain.dto.GenerateInvoiceDTO;
import org.mapstruct.Mapper;

/**
 * Mapper responsável pela conversão entre os objetos {@link GenerateInvoiceDTO}
 * utilizados na camada de transporte (API) e os objetos de comando {@link GenerateInvoiceCommand}
 * utilizados na camada de aplicação (caso de uso).
 * <p>
 * Utiliza a biblioteca MapStruct para gerar automaticamente as implementações de mapeamento.
 * </p>
 *
 * <p>Anotado com {@code @Mapper(componentModel = "spring")} para permitir
 * injeção automática via Spring.</p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Mapper(componentModel = "spring")
public interface GenerateInvoiceMapper {

    /**
     * Converte um DTO de requisição {@link GenerateInvoiceDTO.Request}
     * em um comando de entrada para o caso de uso {@link GenerateInvoiceCommand.Input}.
     *
     * @param request DTO contendo os dados da requisição para geração da fatura.
     * @return Comando de entrada com os dados convertidos.
     */
    GenerateInvoiceCommand.Input toInput(GenerateInvoiceDTO.Request request);

    /**
     * Converte um comando de saída {@link GenerateInvoiceCommand.Output}
     * em um DTO de resposta {@link GenerateInvoiceDTO.Response}.
     *
     * @param output Saída do caso de uso de geração de fatura.
     * @return DTO de resposta contendo os dados da fatura gerada.
     */
    GenerateInvoiceDTO.Response toResponse(GenerateInvoiceCommand.Output output);
}
