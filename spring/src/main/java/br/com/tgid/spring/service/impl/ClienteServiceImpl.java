package br.com.tgid.spring.service.impl;

import br.com.tgid.spring.domains.Cliente;
import br.com.tgid.spring.gateways.repositories.ClienteRepository;
import br.com.tgid.spring.service.ClienteService;
import br.com.tgid.spring.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;

    public Cliente findById(Integer id) {
        return repository.findById(id) // Um Optional ajuda a evitar NullPointerException
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }
}
