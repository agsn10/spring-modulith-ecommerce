package com.app.example.client.mapper.helper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class ClientMapperHelper {

    @Named("defaultSuccessMessage")
    public String defaultSuccessMessage() {
        return "Cliente cadastrado com sucesso";
    }
}
