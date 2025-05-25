package com.app.example.catalog.application.usecase;

import com.app.example.catalog.application.command.RemoveProductFromCatalogCommand;
import com.app.example.catalog.infra.repository.CatalogProductRepository;
import com.app.example.catalog.mapper.RemoveProductFromCatalogMapper;
import com.app.example.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Caso de uso responsável por remover um produto de um catálogo existente.
 * <p>
 * Este use case implementa a interface {@link IUseCase} e realiza a remoção de um produto
 * ao transformar os dados de entrada recebidos por meio do {@link RemoveProductFromCatalogCommand.Input}
 * em uma entidade persistente e chamar o repositório para executar a exclusão.
 * <p>
 * Após a operação, retorna uma instância de {@link RemoveProductFromCatalogCommand.Output}
 * contendo o resultado da operação.
 * </p>
 *
 * <p>Este componente é gerenciado pelo Spring como um {@code @Component} e é qualificado com
 * o nome {@code "removeProductFromCatalogUseCase"}.</p>
 *
 * @see RemoveProductFromCatalogCommand.Input
 * @see RemoveProductFromCatalogCommand.Output
 * @see CatalogProductRepository
 * @see RemoveProductFromCatalogMapper
 * @see IUseCase
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("removeProductFromCatalogUseCase")
public class RemoveProductFromCatalogUseCase implements IUseCase<RemoveProductFromCatalogCommand.Input, Mono<RemoveProductFromCatalogCommand.Output>> {

    /**
     * Repositório responsável pela manipulação dos dados de produtos em catálogos.
     */
    private final CatalogProductRepository catalogProductRepository;

    /**
     * Executa a remoção de um produto de um catálogo com base nos dados de entrada fornecidos.
     *
     * @param input dados de entrada contendo os identificadores do catálogo e do produto a serem removidos
     * @return {@link Mono} contendo a saída com o resultado da operação
     */
    @Override
    public Mono<RemoveProductFromCatalogCommand.Output> execute(RemoveProductFromCatalogCommand.Input input) {
        return catalogProductRepository.deleteByCatalogIdAndProductId(UUID.fromString(input.catalogId()), UUID.fromString(input.productId()))
                .then(Mono.fromSupplier(() ->
                        new RemoveProductFromCatalogCommand.Output(
                                input.productId(),
                                "Produto removido com sucesso"
                        )
                ));
    }
}
