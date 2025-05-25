package com.app.example.catalog.api.resource;

import com.app.example.catalog.api.openapi.CatalogOpenApi;
import com.app.example.catalog.domain.dto.AddProductToCatalogDTO;
import com.app.example.catalog.domain.dto.CreateCatalogDTO;
import com.app.example.catalog.domain.dto.RemoveProductFromCatalogDTO;
import com.app.example.catalog.domain.po.CatalogPO;
import com.app.example.catalog.application.ppi.CatalogPort;
import com.app.example.shared.aop.ReactiveTransactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Controlador REST para operações relacionadas a Catálogos.
 * Responsável por expor os endpoints para criação e manipulação de catálogos e seus produtos.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/catalogs")
@RequiredArgsConstructor
public class CatalogResource implements CatalogOpenApi {

    private final CatalogPort catalogPort;

    /**
     * Cria um novo catálogo.
     *
     * @param request Objeto {@link CatalogPO} com as informações do catálogo a ser criado.
     * @return Um {@link Mono} com o catálogo criado.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ReactiveTransactional
    public Mono<CreateCatalogDTO.Response> create(@Valid @RequestBody CreateCatalogDTO.Request request) {
        return catalogPort.createCatalog(request);
    }

    /**
     * Adiciona um produto a um catálogo existente.
     *
     * @param catalogId ID do catálogo.
     * @param productId ID do produto a ser adicionado.
     * @return Um {@link Mono} vazio que indica a conclusão da operação.
     */
    @PostMapping("/{catalogId}/products")
    @ReactiveTransactional
    public Mono<AddProductToCatalogDTO.Response> addProduct(@PathVariable String catalogId, @RequestParam List<String> productsId) {
        return catalogPort.addProductToCatalog(new AddProductToCatalogDTO.Request(catalogId, productsId));
    }

   /**
    * Remove um produto de um catálogo existente.
    *
    * @param catalogId ID do catálogo.
    * @param productId ID do produto a ser removido.
    * @return Um {@link Mono} vazio que indica a conclusão da operação.
    */
    @DeleteMapping("/{catalogId}/products/{productId}")
    @ReactiveTransactional
    public Mono<RemoveProductFromCatalogDTO.Response> removeProduct(@PathVariable String catalogId, @PathVariable String productId) {
        return catalogPort.removeProductFromCatalog(new RemoveProductFromCatalogDTO.Request(catalogId, productId));
    }
}
