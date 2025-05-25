package com.app.example.product.application.usecase;

import com.app.example.product.application.commnad.ListByCategoryProductCommand;
import com.app.example.product.infra.repository.ProductRepository;
import com.app.example.product.mapper.ListByCategoryProductMapper;
import com.app.example.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * Caso de uso responsável por listar todos os produtos de uma categoria específica.
 * <p>
 * Implementa a interface {@link IUseCase} e lida com a busca de produtos pela categoria no repositório de produtos.
 * Este caso de uso retorna uma lista de produtos que pertencem a uma categoria específica, mapeando-os para o comando de saída {@link ListByCategoryProductCommand.Output}.
 * </p>
 *
 * <p>
 * Exemplo de uso:
 * <pre>{@code
 * ListByCategoryProductCommand.Input input = new ListByCategoryProductCommand.Input("categoria-exemplo");
 * listByCategoryProductUseCase.execute(input).subscribe(...);
 * }</pre>
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("listByCategoryProductUseCase")
public class ListByCategoryProductUseCase implements IUseCase<ListByCategoryProductCommand.Input, Flux<ListByCategoryProductCommand.Output>> {

    /** Repositório responsável pela recuperação de produtos. */
    private final ProductRepository productRepository;

    /** Mapper responsável por converter os dados de {@link ProductPO} para {@link ListByCategoryProductCommand.Output}. */
    private final ListByCategoryProductMapper listByCategoryProductMapper;

    /**
     * Executa o caso de uso de listar os produtos de uma categoria específica.
     * <p>
     * Este método consulta o repositório para encontrar todos os produtos de uma determinada categoria
     * e os converte para a saída definida no comando {@link ListByCategoryProductCommand.Output}.
     * </p>
     *
     * @param input O comando de entrada que contém a categoria dos produtos a serem listados.
     * @return Um {@link Flux} contendo os produtos encontrados, mapeados para o formato de saída {@link ListByCategoryProductCommand.Output}.
     */
    @Override
    public Flux<ListByCategoryProductCommand.Output> execute(ListByCategoryProductCommand.Input input) {
        log.info("Iniciando execução para listar produtos da categoria: {}", input.category());

        return productRepository.findAllByCategory(input.category())
                .doOnSubscribe(subscription -> log.info("Consulta iniciada para a categoria: {}", input.category()))
                .doOnNext(product -> log.debug("Produto encontrado: {}", product.getName()))
                .doOnError(error -> log.error("Erro ao listar produtos da categoria: {}", input.category(), error))
                .map(listByCategoryProductMapper::toCommand)
                .doOnTerminate(() -> log.info("Execução finalizada para a categoria: {}", input.category()));
    }
}
