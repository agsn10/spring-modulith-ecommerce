package com.app.example.shared.usecase;

import reactor.core.publisher.Mono;

public interface IUseCase <T, R>{
    public R execute(T t);
}
