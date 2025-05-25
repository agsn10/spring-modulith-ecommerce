package com.app.example;

import com.app.example.product.domain.dto.CreateProductDTO;
import com.app.example.product.domain.dto.ListByCategoryProductDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

/**
 * Classe de testes para a API de produtos.
 */
@Tag("integration") // Permite rodar testes com: mvn test -Dgroups=integration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductApiTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final String CATEGORY = "Tecnologia";

    /**
     * Testa o endpoint de criação de produto.
     */
    @Test
    @Order(1)
    @Timeout(5) // Garante que o teste falhe se demorar mais que 5 segundos
    @DisplayName("🛠️ Deve criar um novo produto com sucesso")
    void deveCriarProduto() {
        CreateProductDTO.Request request = new CreateProductDTO.Request(
                "Notebook Dell XPS",
                "Notebook ultrafino com processador Intel i7, 16GB RAM",
                new BigDecimal("7999.99"),
                50,
                CATEGORY
        );

        webTestClient.post()
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Produto criado com sucesso");
    }

    /**
     * Testa o endpoint de listagem de produtos por categoria.
     */
    @Test
    @Order(2)
    @Timeout(5) // Garante que o teste falhe se demorar mais que 5 segundos
    @DisplayName("🛠️ Deve listar produtos por categoria")
    void deveListarProdutos() {
        webTestClient.get()
                .uri("/api/products/category/{category}", CATEGORY)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ListByCategoryProductDTO.Response.class)
                .hasSize(1)
                .value(products -> {
                    Assertions.assertEquals(CATEGORY, products.get(0).category());
                });
    }
}