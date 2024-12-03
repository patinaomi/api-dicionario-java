package br.com.tgid.spring.gateways.resources;

import br.com.tgid.spring.domains.Categoria;
import br.com.tgid.spring.gateways.dtos.CategoriaDTO;
import br.com.tgid.spring.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @PostMapping("/create")
    public ResponseEntity<Categoria> insert(@Valid @RequestBody CategoriaDTO categoriaDTO) {

        // Converte CategoriaRequest para Categoria
        Categoria categoriaEntity = new Categoria();
        categoriaEntity.setNome(categoriaDTO.getNome());

        // Cria a categoria e salva no banco
        Categoria categoriaCreated = service.create(categoriaEntity);

        return ResponseEntity.ok().body(categoriaCreated);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        Categoria categoria = service.findById(id);
        return ResponseEntity.ok().body(categoria);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<Categoria> list = service.findAll();
        List<CategoriaDTO> listDto = list.stream().map(CategoriaDTO::new).toList();
        return ResponseEntity.ok().body(listDto);
    }

    //localhost:8080/categorias/page?page=0&linesPerPage=20&orderBy=nome&direction=ASC
    @GetMapping(value = "/page")
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);

        //Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
        Page<CategoriaDTO> listDto = list.map(CategoriaDTO::new);

        return ResponseEntity.ok().body(listDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> update(@Valid @RequestBody CategoriaDTO categoriaDTO, @PathVariable Integer id) {

        // Converte CategoriaRequest para Categoria
        Categoria categoriaEntity = new Categoria();
        categoriaEntity.setId(id);
        categoriaEntity.setNome(categoriaDTO.getNome());

        // Atualiza a categoria
        Categoria categoriaUpdated = service.update(id, categoriaEntity);

        return ResponseEntity.ok().body(new CategoriaDTO(categoriaUpdated));
    }

}
