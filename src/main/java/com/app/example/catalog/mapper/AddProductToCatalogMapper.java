package com.app.example.catalog.mapper;

import com.app.example.catalog.application.command.AddProductToCatalogCommand;
import com.app.example.catalog.domain.dto.AddProductToCatalogDTO;
import com.app.example.catalog.domain.po.CatalogProductPO;
import org.mapstruct.Mapper;

/**
 * Mapper responsável por converter entre os objetos DTO, comandos (Command)
 * e entidades persistentes relacionadas à adição de produtos em catálogos.
 *
 * Utiliza a biblioteca MapStruct para gerar automaticamente a implementação
 * dos métodos de mapeamento.
 *
 * A anotação {@code @Mapper(componentModel = "spring")} indica que o MapStruct
 * deve gerar um bean gerenciado pelo Spring.
 *
 * <p>
 * Conversões suportadas:
 * <ul>
 *     <li>{@link AddProductToCatalogDTO.Request} → {@link AddProductToCatalogCommand.Input}</li>
 *     <li>{@link AddProductToCatalogCommand.Output} → {@link AddProductToCatalogDTO.Response}</li>
 *     <li>{@link AddProductToCatalogCommand.Input} → {@link CatalogProductPO}</li>
 *     <li>{@link CatalogProductPO} → {@link AddProductToCatalogCommand.Output}</li>
 * </ul>
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Mapper(componentModel = "spring")
public interface AddProductToCatalogMapper {

    /**
     * Converte o DTO de requisição de adição de produto em catálogo em um objeto de entrada
     * para o comando {@link AddProductToCatalogCommand}.
     *
     * @param request os dados da requisição para adicionar o produto
     * @return os dados formatados como entrada para o comando
     */
    AddProductToCatalogCommand.Input toInput(AddProductToCatalogDTO.Request request);

    /**
     * Converte o resultado da execução do comando de adição de produto ao catálogo
     * em um DTO de resposta para retorno à camada superior (ex: controller).
     *
     * @param output o resultado da adição do produto ao catálogo
     * @return os dados formatados como resposta da operação
     */
    AddProductToCatalogDTO.Response toResponse(AddProductToCatalogCommand.Output output);

    /**
     * Converte o comando de entrada para uma entidade persistente
     * utilizada pelo repositório.
     *
     * @param input os dados de entrada para adicionar o produto
     * @return a entidade persistente {@link CatalogProductPO}
     */
    CatalogProductPO fromInputToPO(AddProductToCatalogCommand.Input input);

}