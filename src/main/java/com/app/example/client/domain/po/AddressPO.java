package com.app.example.client.domain.po;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

/**
 * Objeto de persistência que representa o endereço de um cliente.
 *
 * Mapeada para a tabela {@code addresses} no banco de dados relacional.
 * Cada instância corresponde a um endereço vinculado a um cliente.
 *
 * Essa classe utiliza anotações do Spring Data para persistência automática.
 * A relação com o cliente é feita através do campo {@code clientId}.
 *
 * @author <a href="mailto:antonio.neto@example.com">Antonio Neto</a>
 */
@Table("addresses")
public class AddressPO {
    /**
     * Identificador único do endereço.
     */
    @Id
    private UUID id;
    /**
     * Identificador do cliente associado a este endereço.
     */
    private UUID clientId;
    /**
     * Nome da rua.
     */
    private String rua;
    /**
     * Número do endereço.
     */
    private String numero;
    /**
     * Nome da cidade.
     */
    private String cidade;
    /**
     * Nome do estado.
     */
    private String estado;
    /**
     * Código de Endereçamento Postal (CEP).
     */
    private String cep;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
