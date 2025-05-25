package com.app.example.order.mapper;

import com.app.example.order.application.command.FindByIdCommand;
import com.app.example.order.domain.dto.FindByIdDTO;
import com.app.example.order.domain.po.OrderPO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FindByIdMapper {

    default FindByIdCommand.Input toInput(FindByIdDTO.Request request) {
        return new FindByIdCommand.Input(request.orderId());
    }

    FindByIdDTO.Response toResponse(FindByIdCommand.Output output);

    default FindByIdCommand.Output toOutput(OrderPO order) {
        return new FindByIdCommand.Output(
                order.getId(),
                order.getClientId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}

