package br.com.tgid.spring.service.impl;

import br.com.tgid.spring.domains.Categoria;
import br.com.tgid.spring.gateways.repositories.CategoriaRepository;
import br.com.tgid.spring.service.CategoriaService;
import br.com.tgid.spring.service.exception.DataIntegrityException;
import br.com.tgid.spring.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository repository;

    public Categoria create(Categoria categoria) {
        return repository.save(categoria);
    }

    public Categoria findById(Integer id) {
        return repository.findById(id) // Um Optional ajuda a evitar NullPointerException
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public List<Categoria> findAll() {
        return repository.findAll();
    }

    public Categoria update(Integer id, Categoria categoria) {
        if(repository.existsById(id)) {
            categoria.setId(id);
            return repository.save(categoria);
        } else {
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName());
        }
    }

    public void delete(Integer id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos associados");
        }
    }
}
