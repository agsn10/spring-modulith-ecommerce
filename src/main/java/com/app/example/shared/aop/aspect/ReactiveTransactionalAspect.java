package com.app.example.shared.aop.aspect;

import com.app.example.shared.aop.ReactiveTransactional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Aspecto que intercepta métodos anotados com {@link ReactiveTransactional}
 * e aplica uma transação reativa usando {@link TransactionalOperator}.
 *
 * <p>Este aspecto deve ser utilizado em projetos com Spring WebFlux e
 * bancos reativos, como R2DBC, garantindo que operações reativas sejam
 * executadas dentro de uma transação.</p>
 *
 * <p>Somente métodos que retornam {@link Mono} ou {@link Flux} são suportados.
 * Caso contrário, será lançada uma {@link IllegalStateException}.</p>
 */
@Aspect
@Component
public class ReactiveTransactionalAspect {

    private final TransactionalOperator txOperator;

    /**
     * Construtor que recebe o {@link TransactionalOperator} a ser utilizado
     * para aplicação da transação.
     *
     * @param txOperator operador transacional reativo
     */
    public ReactiveTransactionalAspect(TransactionalOperator txOperator) {
        this.txOperator = txOperator;
    }

    /**
     * Aplica a transação reativa ao método anotado com {@code @ReactiveTransactional}.
     *
     * @param pjp ponto de junção do método interceptado
     * @return um {@link Mono} ou {@link Flux} com a transação aplicada
     * @throws Throwable se ocorrer qualquer exceção durante a execução do método
     */
    @Around("@annotation(ReactiveTransactional)")
    public Object applyTransaction(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();

        if (result instanceof Mono<?>) {
            return ((Mono<?>) result).as(txOperator::transactional);
        } else if (result instanceof Flux<?>) {
            return ((Flux<?>) result).as(txOperator::transactional);
        } else {
            throw new IllegalStateException("@ReactiveTransactional só pode ser usado em métodos que retornam Mono ou Flux");
        }
    }
}
