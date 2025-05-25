package com.app.example.product.api.resource;

import com.app.example.product.api.openapi.ProductOpenapi;
import com.app.example.product.application.ppi.ProductPort;
import com.app.example.product.domain.dto.CreateProductDTO;
import com.app.example.product.domain.dto.ListByCategoryProductDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para gerenciamento de produtos.
 * <p>
 * Este controlador expõe os endpoints da API para realizar operações de criação de produto
 * e listagem de produtos por categoria. Ele serve como interface entre o cliente (front-end)
 * e a camada de serviço, utilizando o padrão de design de portas e adaptadores (Port and Adapter).
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductResource implements ProductOpenapi {

    private final ProductPort productPort;

    /**
     * Cria um novo produto a partir dos dados fornecidos na requisição.
     *
     * @param request Objeto contendo os dados do produto a ser criado.
     * @return {@link Mono<CreateProductDTO.Request>} representando a criação do produto.
     *         Retorna uma confirmação ou um erro caso a criação não seja bem-sucedida.
     */
    @PostMapping
    public Mono<CreateProductDTO.Response> create(@Valid @RequestBody CreateProductDTO.Request request) {
        return productPort.create(request);
    }

    /**
     * Lista os produtos de uma categoria específica.
     *
     * @param category Categoria pela qual os produtos serão filtrados.
     * @return {@link Flux<ListByCategoryProductDTO.Response>} representando a lista de produtos
     *         pertencentes à categoria informada.
     */
    @GetMapping("/category/{category}")
    public Flux<ListByCategoryProductDTO.Response> listByCategory(@PathVariable String category) {
        return productPort.listByCategory(new ListByCategoryProductDTO.Request(category));
    }

}