package com.app.example.client.api.resource;

import com.app.example.client.api.openapi.ClientOpenapi;
import com.app.example.client.application.ppi.ClientPort;
import com.app.example.client.domain.dto.RegisterClientDTO;
import com.app.example.shared.aop.ReactiveTransactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controlador REST responsável por expor os endpoints relacionados a clientes.
 * <p>
 * Este controlador lida com requisições HTTP para operações envolvendo clientes, como o
 * registro de um novo cliente.
 * <p>
 * Utiliza o {@link ClientPort} como porta de entrada para delegar a lógica de negócio à camada de aplicação.
 * <p>
 * Os endpoints expostos seguem o padrão reativo usando {@link Mono} do Project Reactor.
 *
 * <p><b>Exemplo de requisição:</b></p>
 * <pre>
 * POST /api/clients
 * {
 *   "name": "João da Silva",
 *   "email": "joao@email.com",
 *   "phone": "+55 48 99999-0000",
 *   "rua": "Rua das Flores",
 *   "numero": "123",
 *   "cidade": "Florianópolis",
 *   "estado": "SC",
 *   "cep": "88000-000"
 * }
 * </pre>
 *
 * @author
 *     <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientResource implements ClientOpenapi {

    /**
     * Porta de entrada da aplicação responsável pelas operações com cliente.
     */
    private final ClientPort clientPort;

    /**
     * Endpoint para registrar um novo cliente.
     * <p>
     * Recebe os dados da requisição e retorna uma resposta com o identificador do cliente
     * e uma mensagem de confirmação.
     *
     * @param request Objeto contendo os dados necessários para registrar o cliente.
     * @return {@link Mono} contendo a resposta do registro do cliente.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ReactiveTransactional
    public Mono<RegisterClientDTO.Response> register(@Valid  @RequestBody RegisterClientDTO.Request request) {
        return clientPort.registerClient(request);
    }
}

