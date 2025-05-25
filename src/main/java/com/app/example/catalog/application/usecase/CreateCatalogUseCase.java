package com.app.example.catalog.application.usecase;

import com.app.example.catalog.application.command.CreateCatalogCommand;
import com.app.example.catalog.domain.po.CatalogPO;
import com.app.example.catalog.domain.po.CatalogProductPO;
import com.app.example.catalog.infra.repository.CatalogProductRepository;
import com.app.example.catalog.infra.repository.CatalogRepository;
import com.app.example.catalog.mapper.CreateCatalogMapper;
import com.app.example.product.application.ppi.ProductPort;
import com.app.example.product.domain.dto.ExistsByIdDTO;
import com.app.example.shared.exception.ProductNotFoundException;
import com.app.example.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("createCatalogUseCase")
public class CreateCatalogUseCase implements IUseCase<CreateCatalogCommand.Input, Mono<CreateCatalogCommand.Output>> {

    private final CatalogRepository catalogRepository;
    private final CatalogProductRepository catalogProductRepository;
    private final ProductPort productPort;
    private final CreateCatalogMapper createCatalogMapper;

    @Override
    public Mono<CreateCatalogCommand.Output> execute(CreateCatalogCommand.Input input) {
        log.info("Iniciando criação do catálogo com nome: {}", input.name());

        List<String> productIds = input.productList();

        if (productIds == null || productIds.isEmpty()) {
            log.warn("Lista de produtos vazia ou nula. Criando catálogo sem produtos.");
            return catalogRepository.save(new CatalogPO(input.name()))
                    .doOnNext(savedCatalog -> log.info("Catálogo criado com ID: {}", savedCatalog.getId()))
                    .map(createCatalogMapper::fromPoToOutput);
        }

        return catalogRepository.save(new CatalogPO(input.name()))
                .doOnNext(savedCatalog -> log.info("Catálogo salvo com ID: {}. Iniciando validação e associação de produtos.", savedCatalog.getId()))
                .flatMap(catalogPO -> {
                    UUID catalogId = catalogPO.getId();

                    return Flux.fromIterable(productIds)
                            .flatMap(id -> {
                                log.debug("Verificando existência do produto com ID: {}", id);
                                return productPort.existsById(new ExistsByIdDTO.Request(id))
                                        .flatMap(response -> {
                                            if (response.exist()) {
                                                log.debug("Produto encontrado: {}", id);
                                                return Mono.just(id);
                                            } else {
                                                log.error("Produto não encontrado: {}", id);
                                                return Mono.error(new ProductNotFoundException("Produto não encontrado: " + id));
                                            }
                                        });
                            })
                            .flatMap(validId -> {
                                CatalogProductPO catalogProductPO = new CatalogProductPO();
                                catalogProductPO.setCatalogId(catalogId);
                                catalogProductPO.setProductId(UUID.fromString(validId));
                                log.debug("Salvando associação entre catálogo {} e produto {}", catalogId, validId);
                                return catalogProductRepository.save(catalogProductPO)
                                        .doOnNext(cp -> log.info("Associação salva: catálogo={} produto={}", catalogId, validId));
                            })
                            .then(Mono.just(catalogPO)); // Após salvar todas associações, retorna o catálogo
                })
                .map(createCatalogMapper::fromPoToOutput)
                .doOnSuccess(output -> log.info("Catálogo criado com sucesso. ID: {}", output.id()))
                .doOnError(error -> log.error("Erro ao criar catálogo: {}", error.getMessage(), error));
    }
}
