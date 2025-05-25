package com.app.example.payment.mapper.helper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapperHelper {

    @Named("defaultSuccessMessage")
    public String defaultSuccessMessage() {
        return "Pagamento realizado com sucesso";
    }
}
