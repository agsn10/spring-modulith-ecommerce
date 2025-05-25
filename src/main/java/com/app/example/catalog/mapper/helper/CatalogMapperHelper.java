package com.app.example.catalog.mapper.helper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class CatalogMapperHelper {

    @Named("defaultSuccessMessage")
    public String defaultSuccessMessage() {
        return "Catálogo cadastrado com sucesso";
    }
}
