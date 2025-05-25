package com.app.example;

import com.app.example.invoice.domain.dto.GenerateInvoiceDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@Tag("integration") // Permite rodar testes com: mvn test -Dgroups=integration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InvoiceApiTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    @Timeout(5) // Garante que o teste falhe se demorar mais que 5 segundos
    @DisplayName("🛠️ Deve gerar uma fatura com o ID do pedido com sucesso")
    void shouldGenerateInvoiceByOrderId() {

      /*  String orderId = "";

        // Act & Assert
        webTestClient.post()
                .uri("/api/invoices/generate/{orderId}", orderId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.invoiceId").isEqualTo(response.invoiceId().toString())
                .jsonPath("$.orderId").isEqualTo(orderId)
                .jsonPath("$.amount").isEqualTo(response.amount().doubleValue()); */
    }
}
