package br.com.tgid.spring.service;

import br.com.tgid.spring.domains.ItemPedido;
import br.com.tgid.spring.domains.PagamentoComBoleto;
import br.com.tgid.spring.domains.Pedido;
import br.com.tgid.spring.domains.Produto;
import br.com.tgid.spring.domains.enums.EstadoPagamento;
import br.com.tgid.spring.gateways.repositories.ItemPedidoRepository;
import br.com.tgid.spring.gateways.repositories.PagamentoRepository;
import br.com.tgid.spring.gateways.repositories.PedidoRepository;
import br.com.tgid.spring.gateways.repositories.ProdutoRepository;
import br.com.tgid.spring.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    public Pedido insert(Pedido pedido) {
        pedido.setId(null);
        pedido.setInstante(LocalDateTime.now()); // Usar LocalDateTime.now() para o instante
        pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);

        if (pedido.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
        }

        pedido = repository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());

        for (ItemPedido ip : pedido.getItens()) {
            ip.setDesconto(0.0);
            ip.setPreco(produtoRepository.findById(ip.getProduto().getId())
                    .orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado! Id: " + ip.getProduto().getId() + ", Tipo: " + Produto.class.getName()))
                    .getPreco());
        }

        itemPedidoRepository.saveAll(pedido.getItens());
        return pedido;
    }

}
