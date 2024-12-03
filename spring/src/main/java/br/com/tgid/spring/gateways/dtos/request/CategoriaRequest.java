package br.com.tgid.spring.gateways.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class CategoriaRequest {

    @NotNull(message = "O nome da Categoria não pode ser nula.")
    @Size(max = 80, message = "O nome da categoria não pode ter mais de 80 caracteres.")
    private String nome;

    public CategoriaRequest() {

    }

    public CategoriaRequest(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome() {
        this.nome = nome;
    }
}
