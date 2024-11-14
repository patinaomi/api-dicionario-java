package br.com.tgid.spring.gateways.resources;

import br.com.tgid.spring.domains.Categoria;
import br.com.tgid.spring.gateways.mapper.CategoriaMapper;
import br.com.tgid.spring.gateways.request.CategoriaRequest;
import br.com.tgid.spring.gateways.response.CategoriaResponse;
import br.com.tgid.spring.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    private final CategoriaService service;
    private final CategoriaMapper categoriaMapper;

    @PostMapping("/criar")
    public ResponseEntity<?> criar(@Valid @RequestBody CategoriaRequest request) {
        try {
            Categoria categoria = categoriaMapper.map(request); // Converte CategoriaRequest para Categoria
            Categoria categoriaSalva = service.criar(categoria);
            CategoriaResponse categoriaResponse = categoriaMapper.map(categoriaSalva); // Converte Categoria para CategoriaResponse

            return ResponseEntity.status(HttpStatus.CREATED).body(categoriaResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar a clínica: " + e.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> buscar(@PathVariable Integer id) {
        Categoria categoria = service.buscarPorId(id);
        return ResponseEntity.ok().body(categoria);
    }

}
