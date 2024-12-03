package br.com.tgid.spring.service;

import br.com.tgid.spring.domains.Cliente;
import br.com.tgid.spring.gateways.repositories.ClienteRepository;
import br.com.tgid.spring.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente findById(Integer id) {
        return repository.findById(id) // Um Optional ajuda a evitar NullPointerException
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }
}
