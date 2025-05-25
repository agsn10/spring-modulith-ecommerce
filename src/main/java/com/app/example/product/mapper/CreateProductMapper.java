package com.app.example.product.mapper;

import com.app.example.product.application.commnad.CreateProductCommand;
import com.app.example.product.domain.dto.CreateProductDTO;
import com.app.example.product.domain.po.ProductPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper responsável por converter objetos entre DTOs da camada de apresentação,
 * comandos da camada de aplicação e entidades do domínio relacionados à criação de produtos.
 * <p>
 * Utiliza a biblioteca MapStruct para gerar implementações em tempo de compilação.
 * <p>
 * Define conversões entre:
 * <ul>
 *     <li>{@link CreateProductDTO.Request} → {@link CreateProductCommand.Input}</li>
 *     <li>{@link CreateProductCommand.Output} → {@link CreateProductDTO.Response}</li>
 *     <li>{@link CreateProductCommand.Input} → {@link ProductPO}</li>
 *     <li>{@link ProductPO} → {@link CreateProductCommand.Output}</li>
 * </ul>
 *
 * <p>
 * A anotação {@code componentModel = "spring"} permite que a implementação
 * gerada seja registrada como um bean Spring.
 * </p>
 *
 * @author
 *     <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Mapper(componentModel = "spring")
public interface CreateProductMapper {

    /**
     * Converte um {@link CreateProductDTO.Request} recebido da camada de apresentação
     * em um {@link CreateProductCommand.Input} para ser usado pela camada de aplicação.
     *
     * @param request DTO contendo os dados do produto a ser criado.
     * @return Comando com os dados formatados para o caso de uso.
     */
    CreateProductCommand.Input toInput(CreateProductDTO.Request request);

    /**
     * Converte um {@link CreateProductCommand.Output} produzido pela camada de aplicação
     * em um {@link CreateProductDTO.Response} para ser retornado à camada de apresentação.
     *
     * @param output Resultado da operação de criação de produto.
     * @return DTO com os dados de resposta e mensagem de status.
     */
    CreateProductDTO.Response toResponse(CreateProductCommand.Output output);

    /**
     * Converte um {@link CreateProductCommand.Input} para uma entidade {@link ProductPO}
     * que será persistida no banco de dados.
     *
     * @param input Comando contendo os dados do produto.
     * @return Entidade {@link ProductPO} correspondente.
     */

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "stockQuantity", source = "stockQuantity")
    @Mapping(target = "category", source = "category")
    ProductPO toProductPO(CreateProductCommand.Input input);

    /**
     * Converte uma entidade {@link ProductPO} salva para um {@link CreateProductCommand.Output}
     * incluindo uma mensagem padrão de sucesso.
     *
     * @param productPO Entidade persistida no banco.
     * @return Comando de saída com ID e mensagem.
     */
    @Mapping(target = "message", expression = "java(\"Produto criado com sucesso\")")
    CreateProductCommand.Output toOutput(ProductPO productPO);

}
