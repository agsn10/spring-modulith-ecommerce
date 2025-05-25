package com.app.example.product.mapper;

import com.app.example.product.application.commnad.ExistsByIdCommand;
import com.app.example.product.domain.dto.ExistsByIdDTO;
import org.mapstruct.Mapper;

/**
 * Mapper responsável por converter entre os objetos DTO e os comandos de domínio
 * relacionados à verificação de existência de um produto.
 * <p>
 * Utiliza o MapStruct para realizar o mapeamento entre os tipos de entrada e saída.
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 *
 * @author
 */
@Mapper(componentModel = "spring")
public interface ExistsByIdMapper {

    /**
     * Converte um {@link ExistsByIdDTO.Request} em um {@link ExistsByIdCommand.Input}.
     *
     * @param request DTO com os dados da requisição
     * @return objeto de entrada do comando
     */
    ExistsByIdCommand.Input toInput(ExistsByIdDTO.Request request);

    /**
     * Converte um {@link ExistsByIdCommand.Output} em um {@link ExistsByIdDTO.Response}.
     *
     * @param output objeto de saída do comando
     * @return DTO com a resposta para o cliente
     */
    ExistsByIdDTO.Response toResponse(ExistsByIdCommand.Output output);
}
