package com.app.example.catalog.mapper;

import com.app.example.catalog.application.command.CreateCatalogCommand;
import com.app.example.catalog.domain.dto.CreateCatalogDTO;
import com.app.example.catalog.domain.po.CatalogPO;
import com.app.example.catalog.mapper.helper.CatalogMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 * Mapper responsável por converter entre os objetos DTO, comandos (Command)
 * e entidades relacionadas à criação de catálogos.
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
 *     <li>{@link CreateCatalogDTO.Request} → {@link CreateCatalogCommand.Input}</li>
 *     <li>{@link CreateCatalogCommand.Output} → {@link CreateCatalogDTO.Response}</li>
 *     <li>{@link CreateCatalogCommand.Input} → {@link CatalogPO}</li>
 *     <li>{@link CatalogPO} → {@link CreateCatalogCommand.Output}</li>
 * </ul>
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Mapper(componentModel = "spring", uses = {CatalogMapperHelper.class})
public interface CreateCatalogMapper {

    /**
     * Converte o DTO de requisição de criação de catálogo em um objeto de entrada
     * para o comando {@link CreateCatalogCommand}.
     *
     * @param request os dados da requisição para criação do catálogo
     * @return os dados formatados como entrada para o comando
     */
    CreateCatalogCommand.Input toInput(CreateCatalogDTO.Request request);

    /**
     * Converte o resultado da execução do comando de criação de catálogo
     * em um DTO de resposta para retorno à camada superior (ex: controller).
     *
     * @param output o resultado da criação do catálogo
     * @return os dados formatados como resposta da criação do catálogo
     */
    CreateCatalogDTO.Response toResponse(CreateCatalogCommand.Output output);

    /**
     * Converte os dados de entrada do comando para a entidade {@link CatalogPO},
     * que representa o modelo persistente do catálogo.
     *
     * @param input os dados de entrada do comando
     * @return a entidade {@link CatalogPO} correspondente
     */
    CatalogPO fromInputToPO(CreateCatalogCommand.Input input);

    /**
     * Converte a entidade {@link CatalogPO} em um objeto de saída do comando,
     * atribuindo uma mensagem padrão de sucesso.
     *
     * @param catalogPO a entidade do catálogo persistida
     * @return os dados de saída do comando com mensagem de sucesso
     */
    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "uuidToString"),
            @Mapping(target = "message", expression = "java(\"Catálogo cadastrado com sucesso\")")
    })
    CreateCatalogCommand.Output fromPoToOutput(CatalogPO catalogPO);

    @Named("uuidToString")
    static String uuidToString(java.util.UUID id) {
        return id != null ? id.toString() : null;
    }

}