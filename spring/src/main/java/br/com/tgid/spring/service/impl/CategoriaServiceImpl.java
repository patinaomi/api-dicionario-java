package br.com.tgid.spring.service.impl;

import br.com.tgid.spring.domains.Categoria;
import br.com.tgid.spring.gateways.repositories.CategoriaRepository;
import br.com.tgid.spring.service.CategoriaService;
import br.com.tgid.spring.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public Categoria criar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria buscarPorId(Integer id) {
        return categoriaRepository.findById(id) // Um Optional ajuda a evitar NullPointerException
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    @Override
    public List<Categoria> buscarTodos() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria atualizar(Integer id, Categoria categoria) {
        if(categoriaRepository.existsById(id)) {
            categoria.setId(id);
            return categoriaRepository.save(categoria);
        } else {
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName());
        }
    }

    @Override
    public void deletar(Integer id) {
        if(categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName());
        }

    }
}
