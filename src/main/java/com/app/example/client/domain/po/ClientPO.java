package com.app.example.client.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

/**
 * Objeto de persistência que representa um cliente no sistema.
 *
 * Mapeada para a tabela {@code clients} no banco de dados relacional.
 * Utiliza anotações do Spring Data para suporte à persistência reativa ou JDBC.
 *
 * Cada cliente possui um identificador único, nome, e-mail e telefone de contato.
 *
 * @author <a href="mailto:antonio.neto@example.com">Antonio Neto</a>
 */
@Table("clients")
public class ClientPO {
    /**
     * Identificador único do cliente.
     */
    @Id
    private UUID id;
    /**
     * Nome completo do cliente.
     */
    private String name;
    /**
     * Endereço de e-mail do cliente. Deve ser único no sistema.
     */
    private String email;
    /**
     * Número de telefone do cliente.
     */
    private String phone;

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
