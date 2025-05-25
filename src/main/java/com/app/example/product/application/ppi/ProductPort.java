package com.app.example.product.application.ppi;

import com.app.example.product.domain.dto.*;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Porta primária da aplicação responsável por definir as operações relacionadas a produtos.
 * <p>
 * Essa interface abstrai os casos de uso disponíveis para a criação de produtos, listagem de produtos por categoria,
 * alteração de estoque e consulta de quantidade em estoque, facilitando o desacoplamento entre a camada de apresentação
 * e a lógica de negócio.
 * </p>
 *
 * <p>
 * A adoção dessa abordagem permite que a lógica da aplicação evolua independentemente da tecnologia de entrega (HTTP, gRPC etc.).
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public interface ProductPort {

    /**
     * Cria um novo produto com base nos dados fornecidos no request.
     *
     * @param request Objeto contendo os dados necessários para criar um produto.
     * @return {@link Mono} contendo o objeto de resposta após o processamento ou confirmação da criação do produto.
     */
    Mono<CreateProductDTO.Response> create(@RequestBody CreateProductDTO.Request request);

    /**
     * Lista os produtos com base na categoria informada no request.
     *
     * @param request Objeto contendo a categoria para filtragem dos produtos.
     * @return {@link Flux} contendo a lista de produtos pertencentes à categoria especificada.
     */
    Flux<ListByCategoryProductDTO.Response> listByCategory(ListByCategoryProductDTO.Request request);

    /**
     * Altera a quantidade de estoque de um produto com base nos dados fornecidos no request.
     * <p>
     * Esta operação pode ser utilizada para incrementar ou decrementar o estoque de um produto,
     * como em casos de vendas, devoluções ou ajustes manuais.
     * </p>
     *
     * @param request Objeto contendo o identificador do produto e a quantidade a ser ajustada.
     * @return {@link Mono} contendo os dados da resposta com a nova quantidade em estoque após a alteração.
     */
    Mono<ChangeStockDTO.Response> changeStock(ChangeStockDTO.Request request);

    /**
     * Consulta a quantidade de estoque de um produto com base nos dados fornecidos no request.
     *
     * @param request Objeto contendo o identificador do produto para consulta de estoque.
     * @return {@link Mono} contendo os dados da resposta com a quantidade atual em estoque do produto.
     */
    Mono<GetStockDTO.Response> getStock(GetStockDTO.Request request);

    /**
     * Verifica se existe um produto com o ID especificado.
     *
     * @param request Objeto contendo o identificador do produto a ser verificado.
     * @return {@link Mono} contendo a resposta com o resultado da verificação de existência.
     */
    Mono<ExistsByIdDTO.Response> existsById(ExistsByIdDTO.Request request);
}