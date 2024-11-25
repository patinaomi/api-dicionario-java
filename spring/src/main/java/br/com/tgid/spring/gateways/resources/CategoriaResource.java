package br.com.tgid.spring.gateways.resources;

import br.com.tgid.spring.domains.Categoria;
import br.com.tgid.spring.gateways.request.CategoriaRequest;
import br.com.tgid.spring.gateways.response.CategoriaResponse;
import br.com.tgid.spring.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @PostMapping("/create")
    public ResponseEntity<CategoriaResponse> create(@Valid @RequestBody CategoriaRequest categoriaRequest) {
        // Converte CategoriaRequest para Categoria
        Categoria categoriaEntity = new Categoria();
        categoriaEntity.setNome(categoriaRequest.getNome());
        // Defina os outros campos conforme necessário

        // Cria a categoria e salva no banco
        Categoria categoriaCreated = service.create(categoriaEntity);

        // Converte Categoria para CategoriaResponse
        CategoriaResponse categoriaResponse = new CategoriaResponse(
                categoriaCreated.getId(),
                categoriaCreated.getNome()
        );

        return ResponseEntity.ok().body(categoriaResponse);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        Categoria categoria = service.findById(id);
        return ResponseEntity.ok().body(categoria);
    }
}
