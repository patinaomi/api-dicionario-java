package br.com.tgid.spring.gateways.mapper;

import br.com.tgid.spring.domains.Cidade;
import br.com.tgid.spring.domains.Cliente;
import br.com.tgid.spring.domains.Endereco;
import br.com.tgid.spring.domains.enums.TipoCliente;
import br.com.tgid.spring.gateways.dtos.ClienteNewDTO;
import br.com.tgid.spring.gateways.repositories.CidadeRepository;
import br.com.tgid.spring.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    @Autowired
    private CidadeRepository cidadeRepository;

    public Cliente toEntity(ClienteNewDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setCpfOuCnpj(dto.getCpfOuCnpj());
        cliente.setTipo(TipoCliente.toEnum(dto.getTipo()));

        Cidade cid = cidadeRepository.findById(dto.getCidadeId())
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + dto.getCidadeId() + ", Tipo: " + Cidade.class.getName()));

        Endereco end = new Endereco(null,
                dto.getLogradouro(),
                dto.getNumero(),
                dto.getComplemento(),
                dto.getBairro(),
                dto.getCep(),
                cliente,
                cid);
        cliente.getEnderecos().add(end);

        cliente.getTelefones().add(dto.getTelefone1());
        if (dto.getTelefone2() != null) {
            cliente.getTelefones().add(dto.getTelefone2());
        }
        if (dto.getTelefone3() != null) {
            cliente.getTelefones().add(dto.getTelefone3());
        }

        return cliente;
    }
}
