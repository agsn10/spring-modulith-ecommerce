package com.app.example.shared.handler;

import com.app.example.shared.exception.ClientAlreadyExistsException;
import com.app.example.shared.exception.ProductNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manipulador global de exceções da aplicação.
 * <p>
 * Intercepta exceções comuns de validação em controladores WebFlux e retorna
 * uma resposta estruturada no padrão {@link ProblemDetail} (RFC 7807),
 * contendo informações úteis para o cliente sobre os erros ocorridos.
 * </p>
 */
@RestControllerAdvice
@Order(-2)
public class GlobalExceptionHandler {

    /**
     * Trata exceções de validação lançadas ao tentar vincular dados de requisição (body)
     * a objetos Java anotados com validações.
     *
     * @param ex exceção lançada quando ocorre erro de binding e validação em `@RequestBody`
     * @return uma resposta {@link ProblemDetail} com status 400, descrição do problema
     *         e lista de mensagens de erro.
     */
    @ExceptionHandler(WebExchangeBindException.class)
    public ProblemDetail handleValidationException(WebExchangeBindException ex) {
        List<String> errors = ex.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Erro de validação");
        problemDetail.setType(URI.create("https://api.seusistema.com/errors/validacao"));
        problemDetail.setDetail("Um ou mais campos estão inválidos.");
        problemDetail.setProperty("errors", errors);

        return problemDetail;
    }

    /**
     * Trata exceções de violação de restrições de parâmetros individuais (como `@RequestParam`, `@PathVariable` etc).
     *
     * @param ex exceção lançada quando validações de parâmetros são violadas
     * @return uma resposta {@link ProblemDetail} com status 400, descrição do problema
     *         e lista de mensagens de erro.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Erro de validação");
        problemDetail.setType(URI.create("https://api.seusistema.com/errors/validacao"));
        problemDetail.setDetail("Parâmetros inválidos.");
        problemDetail.setProperty("errors", errors);

        return problemDetail;
    }

    /**
     * Trata exceções lançadas quando um cliente já existe no sistema.
     *
     * @param ex exceção lançada quando um cliente já existe no sistema.
     * @return uma resposta {@link ProblemDetail} com status 409 (Conflito), descrição do problema
     *         e detalhes da exceção.
     */
    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ProblemDetail handleClientAlreadyExists(ClientAlreadyExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Conflito de dados");
        problemDetail.setType(URI.create("https://api.seusistema.com/errors/cliente-existente"));
        problemDetail.setDetail(ex.getMessage());

        return problemDetail;
    }

    /**
     * Trata exceções lançadas quando um produto não é encontrado no sistema.
     *
     * @param ex exceção lançada quando um produto não é encontrado no sistema.
     * @return uma resposta {@link ProblemDetail} com status 404 (Não encontrado), descrição do problema
     *         e detalhes da exceção.
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ProblemDetail handleProductNotFound(ProductNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Produto não encontrado");
        problemDetail.setType(URI.create("https://api.seusistema.com/errors/produto-nao-encontrado"));
        problemDetail.setDetail(ex.getMessage());

        return problemDetail;
    }
}
