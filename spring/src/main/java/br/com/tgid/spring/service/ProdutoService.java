package br.com.tgid.spring.service;

import br.com.tgid.spring.domains.Categoria;
import br.com.tgid.spring.domains.Pedido;
import br.com.tgid.spring.domains.Produto;
import br.com.tgid.spring.gateways.repositories.CategoriaRepository;
import br.com.tgid.spring.gateways.repositories.PedidoRepository;
import br.com.tgid.spring.gateways.repositories.ProdutoRepository;
import br.com.tgid.spring.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        List<Categoria> categorias = categoriaRepository.findAllById(ids);

        return repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);

    }
}
