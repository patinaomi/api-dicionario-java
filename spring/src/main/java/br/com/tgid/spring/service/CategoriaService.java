package br.com.tgid.spring.service;

import br.com.tgid.spring.domains.Categoria;

import java.util.List;

public interface CategoriaService {

    Categoria create(Categoria categoria);
    Categoria findById(Integer id);
    List<Categoria> findAll();
    Categoria update(Integer id, Categoria categoria);
    void delete(Integer id);
}
