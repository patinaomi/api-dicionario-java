package br.com.tgid.spring.gateways.resources;

import br.com.tgid.spring.domains.Cliente;
import br.com.tgid.spring.domains.Produto;
import br.com.tgid.spring.gateways.dtos.ClienteDTO;
import br.com.tgid.spring.gateways.dtos.ProdutoDTO;
import br.com.tgid.spring.gateways.resources.utils.Url;
import br.com.tgid.spring.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        Produto produto = service.findById(id);
        return ResponseEntity.ok().body(produto);
    }

    //http://localhost:8080/produtos/page?nome=or&categorias=1,4

    @GetMapping(value = "/page")
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        String nomeDecoded = Url.decodeParam(nome);
        List<Integer> ids = Url.decodeIntList(categorias);

        Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);

        Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));

        return ResponseEntity.ok().body(listDto);
    }
}
