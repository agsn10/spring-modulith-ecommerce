package com.app.example.product.application.usecase;

import com.app.example.product.application.commnad.ExistsByIdCommand;
import com.app.example.product.infra.repository.ProductRepository;
import com.app.example.product.mapper.ExistsByIdMapper;
import com.app.example.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Caso de uso responsável por verificar se um produto existe no sistema com base no seu identificador.
 * <p>
 * Essa classe implementa a interface {@link IUseCase} e executa a lógica necessária para validar a existência
 * de um produto no repositório, retornando um {@link Mono} com a resposta apropriada.
 * </p>
 *
 * <p>
 * A separação dessa lógica em um caso de uso específico permite maior coesão e clareza na manutenção da aplicação.
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("existsByIdUseCase")
public class ExistsByIdUseCase implements IUseCase<ExistsByIdCommand.Input, Mono<ExistsByIdCommand.Output>> {

    private final ProductRepository productRepository;
    /**
     * Executa o caso de uso de verificação da existência de um produto a partir do identificador fornecido.
     *
     * @param input Objeto contendo o identificador do produto a ser verificado.
     * @return {@link Mono} contendo a resposta com o resultado da verificação (existente ou não).
     */
    @Override
    public Mono<ExistsByIdCommand.Output> execute(ExistsByIdCommand.Input input) {
        return productRepository.findById(UUID.fromString(input.productId()))
                .map(product -> new ExistsByIdCommand.Output(true)) // Produto encontrado
                .defaultIfEmpty(new ExistsByIdCommand.Output(false)); // Produto não encontrado
    }
}