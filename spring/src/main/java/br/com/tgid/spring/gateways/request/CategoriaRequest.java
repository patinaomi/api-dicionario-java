package br.com.tgid.spring.gateways.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoriaRequest {

    @NotNull(message = "O nome da categoria não pode ser nulo")
    @Size(max = 80, message = "O nome da categoria deve ter no máximo 100 caracteres")
    private String nome;
}
