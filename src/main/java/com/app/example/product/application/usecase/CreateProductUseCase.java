package com.app.example.product.application.usecase;

import com.app.example.product.application.commnad.CreateProductCommand;
import com.app.example.product.domain.po.ProductPO;
import com.app.example.product.infra.repository.ProductRepository;
import com.app.example.product.mapper.CreateProductMapper;
import com.app.example.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Caso de uso responsável pela criação de um novo produto.
 * <p>
 * Implementa a interface {@link IUseCase} que define a execução de uma operação com um tipo de entrada
 * e um tipo de saída reativo ({@link Mono} neste caso).
 *
 * <p>
 * Utiliza o padrão reativo do Project Reactor para persistir o produto de forma assíncrona,
 * delegando ao {@link ProductRepository} a operação de salvamento e ao {@link CreateProductMapper}
 * a conversão entre o comando de entrada e a entidade {@code ProductPO}, e da entidade para a resposta de saída.
 *
 * <p>
 * A anotação {@code @Component} registra essa classe como um bean Spring,
 * e {@code @Qualifier("createProductUseCase")} permite sua injeção por nome.
 *
 * @author
 *     <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("createProductUseCase")
public class CreateProductUseCase implements IUseCase<CreateProductCommand.Input, Mono<CreateProductCommand.Output>> {

    /** Repositório responsável por persistir os dados do produto. */
    private final ProductRepository productRepository;

    /** Mapper responsável por converter entre os modelos de entrada/saída e a entidade de domínio. */
    private final CreateProductMapper createProductMapper;

    /**
     * Executa o caso de uso de criação de produto.
     *
     * @param input Comando contendo os dados do produto a ser criado.
     * @return {@link Mono} contendo o resultado da operação de criação,
     *         encapsulado em {@link CreateProductCommand.Output}.
     */
    @Override
    public Mono<CreateProductCommand.Output> execute(CreateProductCommand.Input input) {
        log.info("Iniciando criação de produto: nome={}, categoria={}, preço={}", input.name(), input.category(), input.price());
        return productRepository.save(createProductMapper.toProductPO(input))
                .doOnSuccess(productPO -> log.debug("Produto persistido com sucesso no banco: id={}, nome={}", productPO.getId(), productPO.getName()))
                .map(createProductMapper::toOutput)
                .doOnSuccess(output -> log.info("Produto criado com sucesso: id={}", output.id()))
                .doOnError(error -> log.error("Erro ao criar produto: {}", error.getMessage(), error));
    }
}
