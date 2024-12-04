package br.com.tgid.spring.gateways.resources;

import br.com.tgid.spring.domains.Cliente;
import br.com.tgid.spring.gateways.dtos.ClienteDTO;
import br.com.tgid.spring.gateways.dtos.ClienteNewDTO;
import br.com.tgid.spring.gateways.mapper.ClienteMapper;
import br.com.tgid.spring.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;
    @Autowired
    private ClienteMapper clienteMapper;

    @PostMapping("/create")
    public ResponseEntity<Cliente> insert(@Valid @RequestBody ClienteNewDTO clienteNewDTO) {

        Cliente cliente = clienteMapper.toEntity(clienteNewDTO);
        cliente = service.insert(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        Cliente cliente = service.findById(id);
        return ResponseEntity.ok().body(cliente);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> list = service.findAll();
        List<ClienteDTO> listDto = list.stream().map(ClienteDTO::new).toList();
        return ResponseEntity.ok().body(listDto);
    }

    //localhost:8080/clientes/page?page=0&linesPerPage=20&orderBy=nome&direction=ASC
    @GetMapping(value = "/page")
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);

        Page<ClienteDTO> listDto = list.map(ClienteDTO::new);

        return ResponseEntity.ok().body(listDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> update(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) {

        Cliente clienteEntity = new Cliente();
        clienteEntity.setId(id);
        clienteEntity.setNome(clienteDTO.getNome());
        clienteEntity.setEmail(clienteDTO.getEmail());

        Cliente clienteUpdated = service.update(id, clienteEntity);

        return ResponseEntity.ok().body(new ClienteDTO(clienteUpdated));
    }
}
