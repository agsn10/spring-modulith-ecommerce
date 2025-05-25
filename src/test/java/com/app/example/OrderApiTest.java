package com.app.example;

import com.app.example.order.domain.dto.CancelOrderDTO;
import com.app.example.order.domain.dto.CreateOrderDTO;
import com.app.example.order.domain.dto.MarkOrderAsPaidDTO;
import com.app.example.order.domain.dto.ShipOrderDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration") // Permite rodar testes com: mvn test -Dgroups=integration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderApiTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final String BASE_URL = "/api/orders";

    @Test
    @Order(1)
    @Timeout(5) // Garante que o teste falhe se demorar mais que 5 segundos
    @DisplayName("🛠️ Deve criar uma ordem de pedido com sucesso")
    void shouldCreateOrder() {
        CreateOrderDTO.Request request = new CreateOrderDTO.Request(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                new ArrayList<>(){{
                    add(new CreateOrderDTO.Request.ProductQuantity(UUID.fromString("aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaaa"), 2));
                    add(new CreateOrderDTO.Request.ProductQuantity(UUID.fromString("aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaaa"), 2));
                }}
        );

        webTestClient.post()
                .uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CreateOrderDTO.Response.class)
                .value(response -> {
                    assertThat(response.id()).isNotNull();
                });
    }

    @Test
    @Order(2)
    @Timeout(5) // Garante que o teste falhe se demorar mais que 5 segundos
    @DisplayName("🛠️ Deve cancelar uma ordem de pedido com sucesso")
    void shouldCancelOrder() {
        String orderId = "ddddddd1-dddd-dddd-dddd-dddddddddddd";

        webTestClient.post()
                .uri(BASE_URL + "/" + orderId + "/cancel")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CancelOrderDTO.Response.class)
                .value(response -> {
                    assertThat(response.orderId()).isEqualTo(orderId);
                    assertThat(response.message()).contains("Pedido cancelado com sucesso");
                });
    }

    @Test
    @Order(3)
    @Timeout(5) // Garante que o teste falhe se demorar mais que 5 segundos
    @DisplayName("🛠️ Deve marcar uma ordem como paga com sucesso")
    void shouldMarkOrderAsPaid() {
      /*  String orderId = "ddddddd2-dddd-dddd-dddd-dddddddddddd";

        webTestClient.patch()
                .uri(BASE_URL + "/" + orderId + "/pay")
                .exchange()
                .expectStatus().isOk()
                .expectBody(MarkOrderAsPaidDTO.Response.class)
                .value(response -> {
                    assertThat(response.orderId()).isEqualTo(orderId);
                    assertThat(response.message()).contains("Pedido pago com sucesso");
                }); */
    }

    @Test
    @Order(4)
    @Timeout(5) // Garante que o teste falhe se demorar mais que 5 segundos
    @DisplayName("🛠️ Deve marcar um pedido como enviado com sucesso")
    void shouldShipOrder() {
     /*   String orderId = "mock-order-id";

        webTestClient.patch()
                .uri(BASE_URL + "/" + orderId + "/ship")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ShipOrderDTO.Response.class)
                .value(response -> {
                    assertThat(response.orderId()).isEqualTo(orderId);
                    assertThat(response.message()).contains("enviado");
                });*/
    }
}
