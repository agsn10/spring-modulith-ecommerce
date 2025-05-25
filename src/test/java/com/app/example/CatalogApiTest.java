package com.app.example;

import com.app.example.catalog.domain.dto.AddProductToCatalogDTO;
import com.app.example.catalog.domain.dto.CreateCatalogDTO;
import com.app.example.catalog.domain.dto.RemoveProductFromCatalogDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Tag("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //para manter a mesma instância da classe entre os testes
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CatalogApiTest {

    @Autowired
    private WebTestClient webTestClient;

    private String catalogId;

    @Test
    @Order(1)
    @Timeout(5) // Garante que o teste falhe se demorar mais que 5 segundos
    @DisplayName("🛠️ Deve criar um novo catálogo com sucesso")
    void shouldCreateCatalog() {
        CreateCatalogDTO.Request request = new CreateCatalogDTO.Request("New Catalog", new ArrayList<>(0));

        webTestClient.post()
                .uri("/api/catalogs")
                .contentType(APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Catálogo cadastrado com sucesso");
    }

    @Test
    @Order(2)
    @Timeout(5) // Garante que o teste falhe se demorar mais que 5 segundos
    @DisplayName("🛠️ Deve adicionar produtos ao catálogo")
    void shouldAddProductToCatalog() throws JsonProcessingException {

        CreateCatalogDTO.Request request = new CreateCatalogDTO.Request("New Catalog 1", new ArrayList<>());
        EntityExchangeResult<byte[]> result = webTestClient.post()
                .uri("/api/catalogs")
                .contentType(APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult();
        String responseBody = new String(result.getResponseBody(), StandardCharsets.UTF_8);
        // Usa Jackson ou outra lib para converter o JSON em um objeto
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(responseBody);

        catalogId = json.get("id").asText();
        List<String> productList = new ArrayList<>(){{
            add("aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
            add("aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        }};
        AddProductToCatalogDTO.Request addRequest = new AddProductToCatalogDTO.Request(catalogId, productList);

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/catalogs/{catalogId}/products")
                        .queryParam("productsId", productList.toArray())
                        .build(catalogId))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Produtos adicionados com sucesso");
    }

    @Test
    @Order(3)
    @Timeout(20) // Garante que o teste falhe se demorar mais que 5 segundos
    @DisplayName("🛠️ Deve remover o produto do catálogo com sucesso")
    void shouldRemoveProductFromCatalog() {
        String productId = "aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
        RemoveProductFromCatalogDTO.Request removeRequest = new RemoveProductFromCatalogDTO.Request(catalogId, productId);

        webTestClient.delete()
                .uri("/api/catalogs/{catalogId}/products/{productId}", catalogId, productId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Produto removido com sucesso");
    }
}
