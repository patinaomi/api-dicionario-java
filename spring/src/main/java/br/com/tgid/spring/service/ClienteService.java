package br.com.tgid.spring.service;

import br.com.tgid.spring.domains.Cliente;
import br.com.tgid.spring.gateways.repositories.CidadeRepository;
import br.com.tgid.spring.gateways.repositories.ClienteRepository;
import br.com.tgid.spring.gateways.repositories.EnderecoRepository;
import br.com.tgid.spring.service.exception.DataIntegrityException;
import br.com.tgid.spring.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente insert(Cliente cliente) {
        cliente.setId(null);
        cliente = repository.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());

        return cliente;
    }

    public Cliente findById(Integer id) {
        return repository.findById(id) // Um Optional ajuda a evitar NullPointerException
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente update(Integer id, Cliente cliente) {
        if (repository.existsById(id)) {
            cliente.setId(id);
            return repository.save(cliente);
        } else {
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName());
        }
    }

    public void delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
        }
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

//    public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {
//        Cliente cli = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNewDTO.getTipo()));
//        Cidade cid = cidadeRepository.findOne(clienteNewDTO.getCidadeId());
//        Endereco end = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(), clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cli, cid);
//        cli.getEnderecos().add(end);
//        cli.getTelefones().add(clienteNewDTO.getTelefone1());
//        if (clienteNewDTO.getTelefone2() != null) {
//            cli.getTelefones().add(clienteNewDTO.getTelefone2());
//        }
//        if (clienteNewDTO.getTelefone3() != null) {
//            cli.getTelefones().add(clienteNewDTO.getTelefone3());
//        }
//        return cli;
//    }

}
