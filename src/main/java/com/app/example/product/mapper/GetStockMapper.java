package com.app.example.product.mapper;

import com.app.example.product.application.commnad.GetStockCommand;
import com.app.example.product.domain.dto.GetStockDTO;
import com.app.example.product.domain.po.ProductPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 * Mapper responsável por converter entre diferentes tipos de objetos relacionados à consulta de estoque.
 * <p>
 * A interface usa a anotação {@link Mapper} do MapStruct para gerar implementações automáticas das conversões
 * entre os tipos necessários para o caso de uso de consulta de estoque. O componente gerado é utilizado para
 * mapear dados entre as entidades de domínio e os comandos de entrada/saída da aplicação.
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Mapper(componentModel = "spring")
public interface GetStockMapper {

    /**
     * Converte um objeto {@link ProductPO} para o tipo {@link GetStockCommand.Output}.
     *
     * @param productPO Objeto de produto com dados do banco de dados a ser convertido.
     * @return Objeto de saída do comando contendo os dados do estoque.
     */
    @Mapping(target = "productId", source = "id", qualifiedByName = "uuidToString")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "quantity", source = "stockQuantity")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    GetStockCommand.Output toOutput(ProductPO productPO);

    /**
     * Converte um objeto {@link GetStockDTO.Request} para o tipo {@link GetStockCommand.Input}.
     *
     * @param request Objeto de solicitação de consulta de estoque.
     * @return Objeto de entrada do comando contendo o identificador do produto.
     */
    GetStockCommand.Input toInput(GetStockDTO.Request request);

    /**
     * Converte um objeto {@link GetStockCommand.Output} para o tipo {@link GetStockDTO.Response}.
     *
     * @param output Objeto de saída do comando que contém os dados do estoque.
     * @return Objeto de resposta contendo os dados formatados para o cliente.
     */
    GetStockDTO.Response toResponse(GetStockCommand.Output output);

    @Named("uuidToString")
    static String uuidToString(java.util.UUID id) {
        return id != null ? id.toString() : null;
    }
}