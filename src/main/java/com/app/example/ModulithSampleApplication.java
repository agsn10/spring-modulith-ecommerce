package com.app.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.modulith.Modulithic;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Classe principal de inicialização do sistema Sellers, configurando a aplicação Spring Boot.
 *
 * A aplicação é configurada para:
 * <ul>
 *     <li>Habilitar funcionalidades assíncronas com {@link EnableAsync}.</li>
 *     <li>Ativar funcionalidades do Spring Modulith para uma arquitetura modular com {@link Modulithic}.</li>
 *     <li>Escanear as propriedades de configuração com {@link ConfigurationPropertiesScan}.</li>
 * </ul>
 *
 * As funcionalidades adicionais habilitadas pelas anotações incluem:
 * <ul>
 *     <li>Detecção automática de módulos internos com base na estrutura de pacotes.</li>
 *     <li>Verificação de dependências entre módulos, ajudando a manter um baixo acoplamento.</li>
 *     <li>Geração de documentação sobre os módulos e suas relações.</li>
 *     <li>Teste isolado de módulos.</li>
 *     <li>Simulação de comportamento modular em sistemas monolíticos.</li>
 * </ul>
 */
@EnableAsync
@Modulithic(systemName = "Sellers")
@SpringBootApplication
//@ConfigurationPropertiesScan
public class ModulithSampleApplication {

    /**
     * Método principal que inicializa a aplicação Spring Boot.
     *
     * @param args Argumentos passados para a aplicação ao iniciar.
     */
    public static void main(String[] args) {
        SpringApplication.run(ModulithSampleApplication.class, args);
    }
}
