package br.com.tgid.spring.gateways.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class CategoriaRequest {

    @NotNull(message = "O nome da Categoria não pode ser nula.")
    @Size(max = 80, message = "O nome da categoria não pode ter mais de 80 caracteres.")
    private String nome;
}
