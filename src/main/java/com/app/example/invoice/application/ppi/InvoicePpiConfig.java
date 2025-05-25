package com.app.example.invoice.application.ppi;

import com.app.example.invoice.application.command.GenerateInvoiceCommand;
import com.app.example.invoice.domain.dto.GenerateInvoiceDTO;
import com.app.example.invoice.mapper.GenerateInvoiceMapper;
import com.app.example.shared.usecase.IUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import reactor.core.publisher.Mono;

/**
 * Configuração Spring responsável por fornecer a implementação do {@link InvoicePort}
 * baseada nos casos de uso fornecidos.
 * <p>
 * Esta classe define um {@link Bean} que configura a implementação de {@link InvoicePort},
 * que é utilizada para gerar faturas (Invoice) com base nos comandos fornecidos pelo caso de uso {@link GenerateInvoiceCommand}.
 * O caso de uso é responsável por tratar a lógica de geração da fatura com base nas informações fornecidas.
 * </p>
 *
 *  @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Lazy
@Configuration
public class InvoicePpiConfig {

    /**
     * Método responsável por fornecer a implementação de {@link InvoicePort},
     * utilizando o caso de uso {@link GenerateInvoiceCommand} para gerar faturas.
     * <p>
     * Este método cria e retorna uma implementação de {@link InvoicePort}, a qual chama o caso de uso {@link GenerateInvoiceCommand}
     * para executar a lógica de geração da fatura. A execução do caso de uso depende de um comando de entrada contendo informações
     * sobre a fatura que será gerada, e após a execução, ele retorna um {@link Mono} com a resposta que contém os detalhes da fatura gerada.
     * </p>
     *
     * @param generateInvoice O caso de uso responsável pela geração da fatura, que recebe um comando de entrada e retorna uma resposta.
     * @return Uma implementação de {@link InvoicePort} que invoca o caso de uso {@link GenerateInvoiceCommand} para gerar a fatura.
     *         O retorno é um {@link Mono} contendo a resposta da geração da fatura.
     */
    @Bean("invoicePort")
    public InvoicePort invoicePort(@Qualifier("generateInvoiceUseCase") IUseCase<GenerateInvoiceCommand.Input, Mono<GenerateInvoiceCommand.Output>> generateInvoice,
                                  GenerateInvoiceMapper generateInvoiceMapper) {
        return new InvoicePort() {

            /**
             * Método responsável por gerar uma fatura (Invoice).
             * <p>
             * Este método invoca o caso de uso {@link GenerateInvoiceCommand} para processar a criação da fatura com base nos dados fornecidos
             * no objeto {@link GenerateInvoiceDTO.Request}. Após a execução do caso de uso, o método retorna a resposta contendo
             * os detalhes da fatura gerada.
             * </p>
             *
             * @param request A solicitação contendo os dados necessários para a geração da fatura, como identificador do pedido e valor total.
             * @return Um {@link Mono} contendo a resposta {@link GenerateInvoiceDTO.Response} com os detalhes da fatura gerada.
             */
            @Override
            public Mono<GenerateInvoiceDTO.Response> generateInvoice(GenerateInvoiceDTO.Request request) {
                GenerateInvoiceCommand.Input input = generateInvoiceMapper.toInput(request);
                return generateInvoice.execute(input).map(generateInvoiceMapper::toResponse);
            }
        };
    }
}
