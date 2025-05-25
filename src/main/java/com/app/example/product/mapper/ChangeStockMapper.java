package com.app.example.product.mapper;

import com.app.example.product.application.commnad.ChangeStockCommand;
import com.app.example.product.domain.dto.ChangeStockDTO;
import org.mapstruct.Mapper;

/**
 * Mapper responsável por converter objetos entre os DTOs da camada de apresentação
 * e os comandos da camada de aplicação relacionados à alteração de estoque de produtos.
 * <p>
 * Utiliza a biblioteca MapStruct para gerar implementações em tempo de compilação.
 * <p>
 * Define conversões entre:
 * <ul>
 *     <li>{@link ChangeStockDTO.Request} → {@link ChangeStockCommand.Input}</li>
 *     <li>{@link ChangeStockCommand.Output} → {@link ChangeStockDTO.Response}</li>
 * </ul>
 *
 * <p>
 * A anotação {@code componentModel = "spring"} indica que a implementação gerada
 * será registrada como um bean gerenciado pelo Spring.
 * </p>
 *
 * @author
 *     <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Mapper(componentModel = "spring")
public interface ChangeStockMapper {

    /**
     * Converte um {@link ChangeStockDTO.Request} recebido da camada de apresentação
     * em um {@link ChangeStockCommand.Input} para ser processado pela camada de aplicação.
     *
     * @param request DTO contendo os dados para alteração de estoque.
     * @return Comando com os dados estruturados para o caso de uso.
     */
    ChangeStockCommand.Input toInput(ChangeStockDTO.Request request);

    /**
     * Converte um {@link ChangeStockCommand.Output} retornado pela camada de aplicação
     * em um {@link ChangeStockDTO.Response} para ser enviado de volta à camada de apresentação.
     *
     * @param output Resultado da operação de alteração de estoque.
     * @return DTO com as informações da resposta e mensagem de status.
     */
    ChangeStockDTO.Response toResponse(ChangeStockCommand.Output output);
}