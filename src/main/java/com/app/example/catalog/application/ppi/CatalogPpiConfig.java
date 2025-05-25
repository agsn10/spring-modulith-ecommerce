package com.app.example.catalog.application.ppi;

import com.app.example.catalog.application.command.AddProductToCatalogCommand;
import com.app.example.catalog.application.command.CreateCatalogCommand;
import com.app.example.catalog.application.command.RemoveProductFromCatalogCommand;
import com.app.example.catalog.domain.dto.AddProductToCatalogDTO;
import com.app.example.catalog.domain.dto.CreateCatalogDTO;
import com.app.example.catalog.domain.dto.RemoveProductFromCatalogDTO;
import com.app.example.catalog.mapper.AddProductToCatalogMapper;
import com.app.example.catalog.mapper.CreateCatalogMapper;
import com.app.example.catalog.mapper.RemoveProductFromCatalogMapper;
import com.app.example.shared.usecase.IUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import reactor.core.publisher.Mono;

/**
 * Classe de configuração Spring responsável por instanciar a implementação da interface {@link CatalogPort},
 * que atua como ponto de integração da aplicação para manipulação de catálogos.
 *
 * <p>Essa configuração injeta e conecta os mapeadores e casos de uso necessários para:</p>
 * <ul>
 *     <li>Criar um catálogo</li>
 *     <li>Adicionar um produto a um catálogo existente</li>
 *     <li>Remover um produto de um catálogo existente</li>
 * </ul>
 *
 * <p>Fornece uma instância anônima da interface {@code CatalogPpi}, cujos métodos delegam a execução aos
 * respectivos casos de uso, encapsulando a lógica de transformação dos dados com os mapeadores fornecidos.</p>
 */
@Lazy
@Configuration
public class CatalogPpiConfig {

    /**
     * Define o bean {@link CatalogPort}, responsável por orquestrar as operações sobre o catálogo de produtos,
     * utilizando os casos de uso e mapeadores injetados.
     *
     * @param createCatalogUseCase caso de uso para criação de um novo catálogo
     * @param createCatalogMapper mapeador para transformar dados de entrada e saída da criação de catálogo
     * @param addProductToCatalogUseCase caso de uso para adicionar um produto ao catálogo
     * @param addProductToCatalogMapper mapeador para transformar dados de entrada da adição de produto
     * @param removeProductFromCatalogUseCase caso de uso para remover um produto do catálogo
     * @param removeProductFromCatalogMapper mapeador para transformar dados de entrada da remoção de produto
     * @return implementação da interface {@link CatalogPort} com os métodos delegando aos respectivos casos de uso
     */
    @Bean("catalogPort")
    public CatalogPort catalogPort(
            @Qualifier("createCatalogUseCase") IUseCase<CreateCatalogCommand.Input, Mono<CreateCatalogCommand.Output>> createCatalogUseCase,
            CreateCatalogMapper createCatalogMapper,
            @Qualifier("addProductToCatalogUseCase") IUseCase<AddProductToCatalogCommand.Input, Mono<AddProductToCatalogCommand.Output>> addProductToCatalogUseCase,
            AddProductToCatalogMapper addProductToCatalogMapper,
            @Qualifier("removeProductFromCatalogUseCase") IUseCase<RemoveProductFromCatalogCommand.Input, Mono<RemoveProductFromCatalogCommand.Output>> removeProductFromCatalogUseCase,
            RemoveProductFromCatalogMapper removeProductFromCatalogMapper) {

        return new CatalogPort() {

            /**
             * Cria um novo catálogo delegando ao caso de uso {@code createCatalogUseCase}.
             *
             * @param request objeto contendo os dados necessários para criar o catálogo
             * @return {@link Mono} com a resposta da criação do catálogo
             */
            @Override
            public Mono<CreateCatalogDTO.Response> createCatalog(CreateCatalogDTO.Request request) {
                CreateCatalogCommand.Input input = createCatalogMapper.toInput(request);
                return createCatalogUseCase.execute(input).map(createCatalogMapper::toResponse);
            }

            /**
             * Adiciona um produto a um catálogo delegando ao caso de uso {@code addProductToCatalogUseCase}.
             *
             * @param request objeto contendo os dados necessários para adicionar o produto ao catálogo
             * @return {@link Mono} que sinaliza a conclusão da operação
             */
            @Override
            public Mono<AddProductToCatalogDTO.Response> addProductToCatalog(AddProductToCatalogDTO.Request request) {
                AddProductToCatalogCommand.Input input = addProductToCatalogMapper.toInput(request);
                return addProductToCatalogUseCase.execute(input).map(addProductToCatalogMapper::toResponse);
            }

            /**
             * Remove um produto de um catálogo delegando ao caso de uso {@code removeProductFromCatalogUseCase}.
             *
             * @param request objeto contendo os dados necessários para remover o produto do catálogo
             * @return {@link Mono} que sinaliza a conclusão da operação
             */
            @Override
            public Mono<RemoveProductFromCatalogDTO.Response> removeProductFromCatalog(RemoveProductFromCatalogDTO.Request request) {
                RemoveProductFromCatalogCommand.Input input = removeProductFromCatalogMapper.toInput(request);
                return removeProductFromCatalogUseCase.execute(input).map(removeProductFromCatalogMapper::toResponse);
            }
        };
    }
}
