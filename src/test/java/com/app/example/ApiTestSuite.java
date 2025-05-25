package com.app.example;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

//mvn -X test -Dtest=com.app.example.ApiTestSuite
@Suite
@SelectClasses({
        ProductApiTest.class,
        CatalogApiTest.class,
        ClientApiTest.class,
        //InvoiceApiTest.class,
        //OrderApiTest.class,
        //PaymentApiTest.class,
        ModularityTests.class
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // garante que @Order funcione nos métodos internos
public class ApiTestSuite {
    // Nenhum método é necessário aqui, a anotação já garante a execução dos testes das classes especificadas.
}
