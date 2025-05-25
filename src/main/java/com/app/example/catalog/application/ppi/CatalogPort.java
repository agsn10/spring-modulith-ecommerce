package com.app.example.catalog.application.ppi;

import com.app.example.catalog.domain.dto.AddProductToCatalogDTO;
import com.app.example.catalog.domain.dto.CreateCatalogDTO;
import com.app.example.catalog.domain.dto.RemoveProductFromCatalogDTO;
import reactor.core.publisher.Mono;

/**
 * Interface de entrada (Primary Port Interface - PPI) para operações relacionadas ao gerenciamento de catálogos de produtos.
 * <p>
 * Esta interface define os contratos para a criação de catálogos e as operações de adição e remoção de produtos
 * nos catálogos. Ela serve como ponto de comunicação entre os casos de uso e o sistema, permitindo a execução
 * dessas operações no domínio.
 * <p>
 * Como parte da arquitetura hexagonal, essa interface abstrai a lógica de negócio da implementação de detalhes
 * da infraestrutura, garantindo que os casos de uso possam ser facilmente testados e mantidos.
 *
 * <p>
 * Operações suportadas:
 * <ul>
 *     <li>Criação de catálogos</li>
 *     <li>Adição de produtos a um catálogo</li>
 *     <li>Remoção de produtos de um catálogo</li>
 * </ul>
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public interface CatalogPort {

    /**
     * Cria um novo catálogo com base nas informações fornecidas no DTO de requisição.
     *
     * @param request o objeto {@link CreateCatalogDTO.Request} contendo o nome do catálogo
     *                e, opcionalmente, uma lista de produtos a serem incluídos.
     * @return um {@link Mono} contendo o objeto {@link CreateCatalogDTO.Response} representando
     *         o catálogo criado.
     */
    Mono<CreateCatalogDTO.Response> createCatalog(CreateCatalogDTO.Request request);

    /**
     * Adiciona um produto existente a um catálogo específico, conforme os dados fornecidos na requisição.
     *
     * @param request o objeto {@link AddProductToCatalogDTO.Request} contendo os identificadores do produto
     *                e do catálogo ao qual ele será adicionado.
     * @return um {@link Mono} contendo o objeto {@link AddProductToCatalogDTO.Response} com os detalhes
     *         do produto adicionado ao catálogo.
     */
    Mono<AddProductToCatalogDTO.Response> addProductToCatalog(AddProductToCatalogDTO.Request request);

    /**
     * Remove um produto de um catálogo específico, conforme os dados fornecidos na requisição.
     *
     * @param request o objeto {@link RemoveProductFromCatalogDTO.Request} contendo os identificadores do produto
     *                e do catálogo do qual ele será removido.
     * @return um {@link Mono} contendo o objeto {@link RemoveProductFromCatalogDTO.Response} com a confirmação
     *         da remoção do produto do catálogo.
     */
    Mono<RemoveProductFromCatalogDTO.Response> removeProductFromCatalog(RemoveProductFromCatalogDTO.Request request);
}
