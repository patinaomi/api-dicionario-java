package br.com.tgid.spring.gateways.dtos;

import br.com.tgid.spring.domains.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClienteDTO {

    private Integer id;

    @NotBlank(message = "O nome do Cliente não pode ser nulo.")
    @Size(min = 3, max = 80, message = "O nome do cliente deve ter entre 3 e 80 caracteres.")
    private String nome;

    @NotBlank(message = "O email do Cliente não pode ser nulo.")
    @Size(min = 5, max = 80, message = "O email do cliente deve ter entre 5 e 80 caracteres.")
    @Email(message = "Email inválido.")
    private String email;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente obj) {
        id = obj.getId();
        nome = obj.getNome();
        email = obj.getEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
