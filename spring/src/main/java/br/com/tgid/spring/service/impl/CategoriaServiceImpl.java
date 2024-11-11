package br.com.tgid.spring.service.impl;

import br.com.tgid.spring.domains.Categoria;
import br.com.tgid.spring.repositories.CategoriaRepository;
import br.com.tgid.spring.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public Categoria buscar(Integer id) {
        return categoriaRepository.findById(id) // Um Optional ajuda a evitar NullPointerException
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }
}
