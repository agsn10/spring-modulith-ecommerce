package com.app.example.payment.application.ppi;

import com.app.example.payment.application.command.GetPaymentCommand;
import com.app.example.payment.application.command.ProcessPaymentCommand;
import com.app.example.payment.domain.dto.GetPaymentDTO;
import com.app.example.payment.domain.dto.ProcessPaymentDTO;
import com.app.example.payment.mapper.GetPaymentMapper;
import com.app.example.payment.mapper.ProcessPaymentMapper;
import com.app.example.shared.usecase.IUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import reactor.core.publisher.Mono;

/**
 * Classe de configuração que define o bean {@link PaymentPort}, responsável por expor os serviços de pagamento
 * da aplicação através da interface de porta de entrada (PPI).
 *
 * <p>Ela conecta o caso de uso {@link com.app.example.payment.application.usecase.ProcessPaymentUseCase}
 * com a camada externa (por exemplo, controllers REST), mapeando os dados de entrada e saída através de {@link ProcessPaymentMapper}.</p>
 *
 * <p>Utiliza o padrão Ports & Adapters (Arquitetura Hexagonal) para isolar regras de negócio da infraestrutura.</p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Lazy
@Configuration
public class PaymentPpiConfig {

    private final IUseCase<ProcessPaymentCommand.Input, Mono<ProcessPaymentCommand.Output>> processPaymentUseCase;
    private final ProcessPaymentMapper processPaymentMapper;
    private final IUseCase<GetPaymentCommand.Input, Mono<GetPaymentCommand.Output>> getPaymentUseCase;
    private final GetPaymentMapper getPaymentMapper;

    public PaymentPpiConfig(@Lazy @Qualifier("processPaymentUseCase") IUseCase<ProcessPaymentCommand.Input, Mono<ProcessPaymentCommand.Output>> processPaymentUseCase,
                            ProcessPaymentMapper processPaymentMapper,
                            @Lazy @Qualifier("getPaymentUseCase") IUseCase<GetPaymentCommand.Input, Mono<GetPaymentCommand.Output>> getPaymentUseCase,
                            GetPaymentMapper getPaymentMapper) {
        this.processPaymentUseCase = processPaymentUseCase;
        this.processPaymentMapper = processPaymentMapper;
        this.getPaymentUseCase = getPaymentUseCase;
        this.getPaymentMapper = getPaymentMapper;
    }

    @Bean("paymentPort")
    public PaymentPort paymentPort() {

        return new PaymentPort() {

            /**
             * Processa o pagamento com base nos dados da requisição, delegando ao caso de uso interno e mapeando a resposta.
             *
             * <p>Este método recebe os dados de requisição de pagamento, converte para um comando de processamento,
             * executa o caso de uso e mapeia a resposta para o DTO de resposta.</p>
             *
             * @param request dados da requisição de pagamento
             * @return resposta com os detalhes do pagamento processado, encapsulada em um {@link Mono}
             */
            @Override
            public Mono<ProcessPaymentDTO.Response> processPayment(ProcessPaymentDTO.Request request) {
                ProcessPaymentCommand.Input input = processPaymentMapper.toInput(request);
                return processPaymentUseCase.execute(input).map(processPaymentMapper::toResponse);
            }

            /**
             * Consulta um pagamento com base no ID do pedido.
             *
             * <p>Este método recebe uma requisição com o {@code orderId} e utiliza o caso de uso para buscar
             * os detalhes do pagamento. A resposta é mapeada para o DTO de resposta de pagamento.</p>
             *
             * @param request dados da requisição para buscar um pagamento
             * @return resposta com os detalhes do pagamento consultado, encapsulada em um {@link Mono}
             */
            @Override
            public Mono<GetPaymentDTO.Response> getPaymentByOrderId(GetPaymentDTO.Request request) {
                GetPaymentCommand.Input input = getPaymentMapper.toInput(request);
                return getPaymentUseCase.execute(input).map(getPaymentMapper::toResponse);
            }
        };
    }
}
