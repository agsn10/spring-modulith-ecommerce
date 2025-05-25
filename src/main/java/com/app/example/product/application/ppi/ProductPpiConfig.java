package com.app.example.product.application.ppi;

import com.app.example.product.application.commnad.*;
import com.app.example.product.domain.dto.*;
import com.app.example.product.mapper.*;
import com.app.example.shared.usecase.IUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Classe de configuração do componente PPI (Primary Port Interface) relacionado a produtos.
 * <p>
 * Essa classe define os beans necessários para integrar os casos de uso de criação e listagem de produtos
 * com a interface externa {@link ProductPort}, atuando como uma porta primária do lado da aplicação.
 * <p>
 * O uso de interfaces como {@link IUseCase} e mapeadores garante o desacoplamento entre a camada de apresentação
 * e a lógica de negócio, permitindo fácil manutenção e testes.
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Lazy
@Configuration
public class ProductPpiConfig {


    /**
     * Cria e configura o bean {@link ProductPort}, responsável por expor operações relacionadas a produtos.
     *
     * @param createProductUseCase               Caso de uso para criação de produto.
     * @param createProductMapper                Mapeador para conversão entre DTO e comando de criação.
     * @param listByCategoryProductUseCase       Caso de uso para listagem de produtos por categoria.
     * @param listByCategoryProductMapper        Mapeador para conversão entre DTO e comando de listagem.
     * @param changeStockUseCase                 Caso de uso para alterar o estoque de produtos.
     * @param changeStockMapper                  Mapeador para conversão entre DTO e comando de alteração de estoque.
     * @param getStockUseCase                    Caso de uso para obter o estoque de um produto.
     * @param getStockMapper                     Mapeador para conversão entre DTO e comando de obtenção de estoque.
     * @param existsByIdUseCase                  Caso de uso para verificação da existência de um produto.
     * @param existsByIdMapper                   Mapeador para conversão entre DTO e comando de verificação de existência.
     * @return Implementação anônima de {@link ProductPort}.
     */
    @Bean("productPort")
    public ProductPort productPort(
            @Qualifier("createProductUseCase") IUseCase<CreateProductCommand.Input, Mono<CreateProductCommand.Output>> createProductUseCase,
            CreateProductMapper createProductMapper,
            @Qualifier("listByCategoryProductUseCase") IUseCase<ListByCategoryProductCommand.Input, Flux<ListByCategoryProductCommand.Output>> listByCategoryProductUseCase,
            ListByCategoryProductMapper listByCategoryProductMapper,
            @Qualifier("changeStockUseCase") IUseCase<ChangeStockCommand.Input, Mono<ChangeStockCommand.Output>> changeStockUseCase,
            ChangeStockMapper changeStockMapper,
            @Qualifier("getStockUseCase") IUseCase<GetStockCommand.Input, Mono<GetStockCommand.Output>> getStockUseCase,
            GetStockMapper getStockMapper,
            @Qualifier("existsByIdUseCase") IUseCase<ExistsByIdCommand.Input, Mono<ExistsByIdCommand.Output>> existsByIdUseCase,
            ExistsByIdMapper existsByIdMapper
    ) {
        return new ProductPort() {

            /**
             * Realiza a criação de um produto.
             *
             * @param request Dados do produto a ser criado.
             * @return Mono com a resposta contendo o produto criado.
             */
            @Override
            public Mono<CreateProductDTO.Response> create(CreateProductDTO.Request request) {
                CreateProductCommand.Input input = createProductMapper.toInput(request);
                return createProductUseCase.execute(input).map(createProductMapper::toResponse);
            }

            /**
             * Lista produtos com base em uma categoria.
             *
             * @param request Dados da requisição com a categoria.
             * @return Fluxo de produtos encontrados.
             */
            @Override
            public Flux<ListByCategoryProductDTO.Response> listByCategory(ListByCategoryProductDTO.Request request) {
                ListByCategoryProductCommand.Input input = listByCategoryProductMapper.toInput(request);
                return listByCategoryProductUseCase.execute(input).map(listByCategoryProductMapper::toResponse);
            }

            /**
             * Altera a quantidade de estoque de um produto com base nos dados fornecidos no request.
             * <p>
             * Esta operação pode ser utilizada para incrementar ou decrementar o estoque de um produto,
             * como em casos de vendas, devoluções ou ajustes manuais.
             * </p>
             *
             * @param request Objeto contendo o identificador do produto e a quantidade a ser ajustada.
             * @return {@link Mono} contendo os dados da resposta com a nova quantidade em estoque.
             */
            @Override
            public Mono<ChangeStockDTO.Response> changeStock(ChangeStockDTO.Request request) {
                ChangeStockCommand.Input input = changeStockMapper.toInput(request);
                return changeStockUseCase.execute(input).map(changeStockMapper::toResponse);
            }

            /**
             * Obtém a quantidade de estoque de um produto.
             * <p>
             * Este método consulta a quantidade disponível no estoque para o produto identificado
             * pelo ID fornecido na requisição.
             * </p>
             *
             * @param request Dados contendo o ID do produto a ser consultado.
             * @return {@link Mono} com a resposta contendo a quantidade de estoque.
             */
            @Override
            public Mono<GetStockDTO.Response> getStock(GetStockDTO.Request request) {
                GetStockCommand.Input input = getStockMapper.toInput(request);
                return getStockUseCase.execute(input).map(getStockMapper::toResponse);
            }

            /**
             * Verifica se um produto existe a partir do seu ID.
             * <p>
             * Esse método é utilizado para validar a existência de um produto específico
             * com base no identificador fornecido na requisição.
             * </p>
             *
             * @param request Objeto contendo o ID do produto a ser verificado.
             * @return {@link Mono} com a resposta indicando se o produto existe.
             */
            @Override
            public Mono<ExistsByIdDTO.Response> existsById(ExistsByIdDTO.Request request) {
                ExistsByIdCommand.Input input = existsByIdMapper.toInput(request);
                return existsByIdUseCase.execute(input).map(existsByIdMapper::toResponse);
            }

        };
    }
}
