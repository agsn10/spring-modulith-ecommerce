package com.app.example;

import com.app.example.client.domain.dto.RegisterClientDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

//Se quiser que o teste grave no banco...
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebFluxTest(ClientResource.class)
//Não mockar(TestMockConfig) o ClientPort, ou usar uma implementação real dele
@Tag("integration") // Permite rodar testes com: mvn test -Dgroups=integration
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientApiTest {

    @Autowired
    private WebTestClient webTestClient;

    private RegisterClientDTO.Request validClientRequest;

    @BeforeEach
    public void setUp() {
        // Configura um objeto de cliente válido para os testes
        validClientRequest = new RegisterClientDTO.Request(
                "João da Silva",        // name
                "joao@email.com",             // email
                "48999990009",                // phone
                "Rua das Flores",             // rua
                "123",                        // numero
                "Florianópolis",              // cidade
                "SC",                         // estado
                "88000-000"                   // cep
        );
    }

    @Test
    @Order(1)
    @Timeout(5) // Garante que o teste falhe se demorar mais que 5 segundos
    @DisplayName("🛠️ Deve criar um novo cliente com sucesso")
    public void registerClient_Success() {
        // Realiza a requisição POST e valida a resposta
        webTestClient.post()
                .uri("/api/clients")
                .bodyValue(validClientRequest)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody()
                .jsonPath("$.message").isEqualTo("Cliente cadastrado com sucesso");
    }

    @Test
    @Order(2)
    @Timeout(5) // Garante que o teste falhe se demorar mais que 5 segundos
    @DisplayName("🛠️ Deve lançar uma exception caso o cliente exista")
    public void registerClient_ClientAlreadyExists() {
        // Realiza a requisição POST e valida a resposta de erro
        webTestClient.post()
                .uri("/api/clients")
                .bodyValue(validClientRequest)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody()
                .jsonPath("$.detail").isEqualTo("Já existe um cliente com este e-mail.");
    }

    @Test
    @Order(3)
    @Timeout(5) // Garante que o teste falhe se demorar mais que 5 segundos
    @DisplayName("🛠️ Deve lançar uma exception de dados inválidos")
    public void registerClient_InvalidRequest() {
        // Cria um objeto de requisição inválido (sem nome, por exemplo)
       RegisterClientDTO.Request invalidRequest = new RegisterClientDTO.Request(
               null,                   // name
               "maria@email.com",            // email
               "48999990010",                // phone
               "Rua das Flores",             // rua
               "123",                        // numero
               "Florianópolis",              // cidade
               "SC",                         // estado
               "88000-000"                   // cep
       );

        // Realiza a requisição POST e valida a resposta de erro
        webTestClient.post()
                .uri("/api/clients")
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$.detail").isEqualTo("Um ou mais campos estão inválidos.");
    }
}
