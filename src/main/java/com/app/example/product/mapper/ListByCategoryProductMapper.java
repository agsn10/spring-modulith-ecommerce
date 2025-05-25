package com.app.example.product.mapper;

import com.app.example.product.application.commnad.ListByCategoryProductCommand;
import com.app.example.product.domain.dto.ListByCategoryProductDTO;
import com.app.example.product.domain.po.ProductPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Mapper responsável por converter entre {@link ListByCategoryProductDTO} e {@link ListByCategoryProductCommand}.
 * <p>
 * Utiliza MapStruct para mapear automaticamente os dados entre a camada de apresentação (DTO)
 * e a camada de aplicação (Command), facilitando a comunicação entre essas camadas.
 * </p>
 *
 * <p>Implementado automaticamente pelo Spring com base na configuração do componente.</p>
 *
 * <p>
 * O Mapper realiza a conversão entre os objetos DTO e Command para garantir a fluidez do fluxo de dados
 * na aplicação, principalmente ao transferir informações entre a camada de apresentação e a lógica de negócios.
 * </p>
 *
 * @author
 *     <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Mapper(componentModel = "spring")
public interface ListByCategoryProductMapper {

    /**
     * Converte um DTO de requisição para o comando de entrada.
     * <p>
     * Este método converte os dados de entrada de um DTO para o comando de entrada
     * utilizado no caso de uso {@link ListByCategoryProductCommand}.
     * </p>
     *
     * @param request o DTO contendo os dados de entrada da requisição
     * @return o comando de entrada correspondente
     */
    ListByCategoryProductCommand.Input toInput(ListByCategoryProductDTO.Request request);

    /**
     * Converte a saída do comando para um DTO de resposta.
     * <p>
     * Este método converte o comando de saída da lógica de negócios para um DTO de resposta,
     * facilitando a transferência de dados para a camada de apresentação.
     * </p>
     *
     * @param output a saída do comando com os dados do produto
     * @return o DTO de resposta correspondente
     */
    ListByCategoryProductDTO.Response toResponse(ListByCategoryProductCommand.Output output);

    /**
     * Converte um {@link ProductPO} para o comando de saída {@link ListByCategoryProductCommand.Output}.
     * <p>
     * Este método converte a entidade {@link ProductPO} (que representa um produto no banco de dados)
     * para um comando de saída utilizado nos casos de uso, garantindo que os dados sejam transferidos corretamente.
     * </p>
     *
     * @param productPO a entidade {@link ProductPO} a ser convertida
     * @return o comando de saída {@link ListByCategoryProductCommand.Output} correspondente
     */

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "stockQuantity", target = "stockQuantity")
    @Mapping(source = "category", target = "category")
    ListByCategoryProductCommand.Output toCommand(ProductPO productPO);
}