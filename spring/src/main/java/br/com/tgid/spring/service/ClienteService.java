package br.com.tgid.spring.service;

import br.com.tgid.spring.domains.Cliente;

public interface ClienteService {

    Cliente findById(Integer id);
}
