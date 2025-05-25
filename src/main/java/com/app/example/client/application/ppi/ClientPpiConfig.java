package com.app.example.client.application.ppi;

import com.app.example.client.application.command.RegisterClientCommand;
import com.app.example.client.domain.dto.RegisterClientDTO;
import com.app.example.client.mapper.RegisterClientMapper;
import com.app.example.shared.usecase.IUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import reactor.core.publisher.Mono;

/**
 * Classe de configuração responsável por expor as implementações das portas PPI (Primary Port Interface).
 * <p>
 * Esta configuração conecta a interface {@link ClientPort} a uma implementação baseada em um caso de uso
 * genérico {@link IUseCase}, permitindo o uso do padrão Ports and Adapters (arquitetura hexagonal).
 * <p>
 * O bean registrado aqui delega a execução do registro de cliente ao {@code registerClientUseCase}.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Lazy
@Configuration
public class ClientPpiConfig {

    /**
     * Cria uma implementação de {@link ClientPort} utilizando um {@link IUseCase} para tratar o
     * registro de clientes.
     *
     * @param registerClientUseCase Caso de uso responsável pelo registro de clientes.
     * @return Implementação da interface {@link ClientPort}.
     */
    @Bean("clientPort")
    public ClientPort clientPort(
            @Qualifier("registerClientUseCase")
            IUseCase<RegisterClientCommand.Input, Mono<RegisterClientCommand.Output>> registerClientUseCase,
            RegisterClientMapper registerClientMapper) {

        return new ClientPort() {

            /**
             * Executa o caso de uso de registro de cliente, convertendo o {@link RegisterClientDTO.Request}
             * para o formato esperado pela camada de domínio.
             *
             * @param request DTO com os dados de entrada para o registro do cliente.
             * @return Mono com a resposta do registro do cliente.
             */
            @Override
            public Mono<RegisterClientDTO.Response> registerClient(RegisterClientDTO.Request request) {
                RegisterClientCommand.Input input = registerClientMapper.toInput(request);
                return registerClientUseCase.execute(input).map(registerClientMapper::toResponse);
            }
        };
    }
}
