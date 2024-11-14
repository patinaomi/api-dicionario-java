package br.com.tgid.spring.service;

import br.com.tgid.spring.domains.Categoria;

import java.util.List;

public interface CategoriaService {

    Categoria criar(Categoria categoria);
    Categoria buscarPorId(Integer id);
    List<Categoria> buscarTodos();
    Categoria atualizar(Integer id, Categoria categoria);
    void deletar(Integer id);
}
