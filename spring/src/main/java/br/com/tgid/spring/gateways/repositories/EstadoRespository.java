package br.com.tgid.spring.gateways.repositories;

import br.com.tgid.spring.domains.Cidade;
import br.com.tgid.spring.domains.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRespository extends JpaRepository<Estado, Integer> {
}
