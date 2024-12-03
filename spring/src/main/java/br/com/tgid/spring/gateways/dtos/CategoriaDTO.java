package br.com.tgid.spring.gateways.dtos;

import br.com.tgid.spring.domains.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class CategoriaDTO {

    private Integer id;

    @NotBlank(message = "O nome da Categoria não pode ser nula.")
    @Size(min = 5, max = 80, message = "O nome da categoria deve ter entre 5 e 80 caracteres.")
    private String nome;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Categoria obj) {
        id = obj.getId();
        nome = obj.getNome();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
