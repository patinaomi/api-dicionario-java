package br.com.tgid.spring.service.impl;

import br.com.tgid.spring.domains.Pedido;
import br.com.tgid.spring.gateways.repositories.PedidoRepository;
import br.com.tgid.spring.service.PedidoService;
import br.com.tgid.spring.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;

    public Pedido buscar(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }
}
