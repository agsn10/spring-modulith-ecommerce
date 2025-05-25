package com.app.example.catalog.mapper;

import com.app.example.catalog.application.command.RemoveProductFromCatalogCommand;
import com.app.example.catalog.domain.dto.RemoveProductFromCatalogDTO;
import com.app.example.catalog.domain.po.CatalogProductPO;
import org.mapstruct.Mapper;

/**
 * Mapper responsável por converter entre as estruturas relacionadas à remoção de produtos do catálogo,
 * como DTOs, comandos e entidades persistidas.
 * <p>
 * Utiliza o MapStruct para gerar automaticamente as implementações das conversões.
 * Está configurado como um componente do Spring via {@code componentModel = "spring"}.
 * </p>
 *
 * <p>
 * Este mapper é utilizado no caso de uso {@code RemoveProductFromCatalogUseCase},
 * permitindo a conversão entre camadas da aplicação (DTOs, comandos e entidades).
 * </p>
 *
 * @see RemoveProductFromCatalogCommand.Input
 * @see RemoveProductFromCatalogCommand.Output
 * @see RemoveProductFromCatalogDTO.Request
 * @see RemoveProductFromCatalogDTO.Response
 * @see CatalogProductPO
 */
@Mapper(componentModel = "spring")
public interface RemoveProductFromCatalogMapper {

    /**
     * Converte os dados de entrada do comando em uma instância de {@link CatalogProductPO}.
     *
     * @param input dados de entrada contendo os identificadores do catálogo e do produto
     * @return entidade {@link CatalogProductPO} representando a associação a ser removida
     */
    CatalogProductPO fromInputToPO(RemoveProductFromCatalogCommand.Input input);

    /**
     * Converte o DTO {@link RemoveProductFromCatalogDTO.Request} em um comando {@link RemoveProductFromCatalogCommand.Input}.
     *
     * @param request DTO contendo os dados de requisição para remoção do produto
     * @return comando com os mesmos dados para ser usado na camada de aplicação
     */
    RemoveProductFromCatalogCommand.Input toInput(RemoveProductFromCatalogDTO.Request request);

    /**
     * Converte a saída do comando {@link RemoveProductFromCatalogCommand.Output}
     * para o DTO {@link RemoveProductFromCatalogDTO.Response}.
     *
     * @param output objeto de saída contendo o resultado da operação
     * @return DTO de resposta com os dados formatados para envio ao cliente
     */
    RemoveProductFromCatalogDTO.Response toResponse(RemoveProductFromCatalogCommand.Output output);
}
