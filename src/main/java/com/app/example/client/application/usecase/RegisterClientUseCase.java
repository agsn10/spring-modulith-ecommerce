package com.app.example.client.application.usecase;

import com.app.example.client.application.command.RegisterClientCommand;
import com.app.example.client.domain.po.AddressPO;
import com.app.example.client.domain.po.ClientPO;
import com.app.example.client.infra.repository.AddressRepository;
import com.app.example.client.infra.repository.ClientRepository;
import com.app.example.client.mapper.RegisterClientMapper;
import com.app.example.shared.exception.ClientAlreadyExistsException;
import com.app.example.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Caso de uso responsável pelo registro de um novo cliente no sistema.
 * <p>
 * Este caso de uso orquestra a lógica necessária para validar e persistir
 * os dados de um novo cliente, garantindo que todas as regras de negócio
 * sejam respeitadas antes de concluir o cadastro.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("registerClientUseCase")
public class RegisterClientUseCase implements IUseCase<RegisterClientCommand.Input, Mono<RegisterClientCommand.Output>> {

    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final RegisterClientMapper registerClientMapper;

    /**
     * Executa o caso de uso de registro de cliente.
     *
     * @param input os dados de entrada contendo informações do cliente a ser registrado
     * @return um {@link Mono} que emitirá {@link RegisterClientCommand.Output} contendo
     *         os dados do cliente registrado ou erro em caso de falha
     */
    @Override
    public Mono<RegisterClientCommand.Output> execute(RegisterClientCommand.Input input) {
        log.info("Iniciando processo de registro de cliente: {}", input);
        return clientRepository.existsByEmail(input.email())
                .flatMap(exists -> {
                    if (exists) {
                        log.warn("Já existe um cliente com o e-mail: {}", input.email());
                        return Mono.error(new ClientAlreadyExistsException("Já existe um cliente com este e-mail."));
                    }
                    ClientPO clientPO = registerClientMapper.toClientPO(input);
                    return clientRepository.save(clientPO)
                            .doOnSubscribe(subscription -> log.debug("Salvando cliente no repositório..."))
                            .flatMap(savedClient -> {
                                log.info("Cliente salvo com sucesso: {}", savedClient.toString());
                                AddressPO addressPO = registerClientMapper.toAddressPO(input);
                                addressPO.setClientId(savedClient.getId());
                                return addressRepository.save(addressPO)
                                        .doOnSuccess(addr -> log.info("Endereço salvo com sucesso: {}", addr))
                                        .thenReturn(registerClientMapper.toOutput(savedClient));
                            });
                })
                .doOnNext(output -> log.info("Resposta de saída gerada: {}", output))
                .doOnError(error -> log.error("Erro ao registrar cliente: {}", error.getMessage(), error));
    }
}