package com.app.example.catalog.application.usecase;

import com.app.example.catalog.application.command.AddProductToCatalogCommand;
import com.app.example.catalog.domain.po.CatalogProductPO;
import com.app.example.catalog.infra.repository.CatalogProductRepository;
import com.app.example.catalog.mapper.AddProductToCatalogMapper;
import com.app.example.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Caso de uso responsável por realizar a operação de adição de um produto a um catálogo.
 *
 * Esta classe implementa a interface {@link IUseCase}, recebendo um comando de entrada
 * do tipo {@link AddProductToCatalogCommand.Input} e retornando um resultado assíncrono
 * do tipo {@link Mono} com {@link AddProductToCatalogCommand.Output}.
 *
 * A lógica principal consiste em mapear os dados de entrada para uma entidade persistente,
 * salvá-la no repositório e retornar uma resposta mapeada para a camada superior.
 *
 * <p>
 * Anotações utilizadas:
 * <ul>
 *     <li>{@code @Component} – Indica que esta classe é um componente Spring.</li>
 *     <li>{@code @Slf4j} – Provê logging via SLF4J.</li>
 *     <li>{@code @RequiredArgsConstructor} – Gera construtor com os campos finais injetados.</li>
 *     <li>{@code @Qualifier("addProductToCatalogUseCase")} – Permite diferenciação na injeção de dependências.</li>
 * </ul>
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("addProductToCatalogUseCase")
public class AddProductToCatalogUseCase implements IUseCase<AddProductToCatalogCommand.Input, Mono<AddProductToCatalogCommand.Output>> {

    private final CatalogProductRepository catalogProductRepository;
    private final AddProductToCatalogMapper addProductToCatalogMapper;

    /**
     * Executa o caso de uso de adição de produto ao catálogo.
     *
     * @param input os dados necessários para adicionar o produto
     * @return um {@link Mono} contendo a resposta da operação com sucesso
     */
    @Override
    public Mono<AddProductToCatalogCommand.Output> execute(AddProductToCatalogCommand.Input input) {
        log.info("Iniciando adição de produtos ao catálogo: {}", input.catalogId());

        return Flux.fromIterable(input.productList())
                .doOnNext(productId -> log.info("Processando produto: {}", productId))
                .flatMap(productId -> {
                    log.info("Salvando produto no repositório: {}", productId);
                    return catalogProductRepository
                            .save(new CatalogProductPO(UUID.fromString(input.catalogId()), UUID.fromString(productId)))
                            .doOnSuccess(saved -> log.info("Produto salvo com sucesso: {}", saved.getProductId()));
                })
                .map(saved -> saved.getProductId().toString()) // <- Extrai apenas o productId como String
                .collectList()
                .map(savedProductIds -> {
                    log.info("Todos os produtos foram salvos: {}", savedProductIds);
                    return new AddProductToCatalogCommand.Output(savedProductIds, "Produtos adicionados com sucesso");
                });
    }
}
