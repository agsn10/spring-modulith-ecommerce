package com.app.example.client.application.ppi;

import com.app.example.client.domain.dto.RegisterClientDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Interface de entrada (Primary Port Interface - PPI) para operações relacionadas ao cliente.
 * <p>
 * Esta interface define os contratos para as operações relacionadas ao registro de clientes.
 * Ela serve como ponto de comunicação entre os casos de uso da aplicação e o sistema, permitindo a execução
 * dessas operações no domínio.
 * <p>
 * Como parte da arquitetura hexagonal, essa interface abstrai a lógica de negócio da implementação de detalhes
 * da infraestrutura, garantindo que os casos de uso possam ser facilmente testados e mantidos.
 *
 * @author Antonio Neto
 */
public interface ClientPort {

    /**
     * Registra um novo cliente.
     *
     * @param request Dados necessários para registrar um cliente, como informações pessoais e de contato.
     * @return Um {@link Mono} contendo a resposta do processo de registro do cliente.
     */
    Mono<RegisterClientDTO.Response> registerClient(RegisterClientDTO.Request request);
}
