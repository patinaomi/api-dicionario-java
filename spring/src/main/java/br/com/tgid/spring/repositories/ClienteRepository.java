package br.com.tgid.spring.repositories;

import br.com.tgid.spring.domains.Categoria;
import br.com.tgid.spring.domains.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
