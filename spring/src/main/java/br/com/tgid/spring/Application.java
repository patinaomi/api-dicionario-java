package br.com.tgid.spring;

import br.com.tgid.spring.domains.Categoria;
import br.com.tgid.spring.domains.Cidade;
import br.com.tgid.spring.domains.Cliente;
import br.com.tgid.spring.domains.Endereco;
import br.com.tgid.spring.domains.Estado;
import br.com.tgid.spring.domains.Pagamento;
import br.com.tgid.spring.domains.PagamentoComBoleto;
import br.com.tgid.spring.domains.PagamentoComCartao;
import br.com.tgid.spring.domains.Pedido;
import br.com.tgid.spring.domains.Produto;
import br.com.tgid.spring.domains.enums.EstadoPagamento;
import br.com.tgid.spring.domains.enums.TipoCliente;
import br.com.tgid.spring.gateways.repositories.CategoriaRepository;
import br.com.tgid.spring.gateways.repositories.CidadeRepository;
import br.com.tgid.spring.gateways.repositories.ClienteRepository;
import br.com.tgid.spring.gateways.repositories.EnderecoRepository;
import br.com.tgid.spring.gateways.repositories.EstadoRespository;
import br.com.tgid.spring.gateways.repositories.PagamentoRepository;
import br.com.tgid.spring.gateways.repositories.PedidoRepository;
import br.com.tgid.spring.gateways.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@RequiredArgsConstructor
@SpringBootApplication
public class Application implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final EstadoRespository estadoRespository;
    private final CidadeRepository cidadeRepository;
    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final PedidoRepository pedidoRepository;
    private final PagamentoRepository pagamentoRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Criamos as categorias
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");

        // Salvamos
        categoriaRepository.saveAll(Arrays.asList(cat1, cat2));

        // Criamos os produtos
        Produto p1 = new Produto(null, "Computador", 2000.00);
        Produto p2 = new Produto(null, "Impressora", 800.00);
        Produto p3 = new Produto(null, "Mouse", 80.00);

        // Na categoria 1 eu incluo os produtos 1, 2 e 3
        cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));

        // Na categoria 2 eu incluo o produto 2
        cat2.getProdutos().add(p2);

        // No Produto 1, eu incluo a categoria 1
        p1.getCategorias().add(cat1);

        // No Produto 2, eu incluo as categorias 1 e 2
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2));

        // No Produto 3, eu incluo a categoria 1
        p3.getCategorias().add(cat1);

        // Salvando os produtos, que já estão associados às categorias
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

        // Criando os estados
        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");

        // Criando as cidades
        Cidade c1 = new Cidade(null, "Uberlândia", est1);
        Cidade c2 = new Cidade(null, "São Paulo", est2);
        Cidade c3 = new Cidade(null, "Campinas", est2);

        est1.getCidades().add(c1);
        est2.getCidades().addAll(Arrays.asList(c2, c3));

        estadoRespository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

        Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
        cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

        Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
        Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

        cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

        clienteRepository.saveAll(Arrays.asList(cli1));
        enderecoRepository.saveAll(Arrays.asList(e1, e2));

        Pedido ped1 = new Pedido(null, LocalDateTime.of(2017, 9, 30, 15, 30, 0), cli1, e1);
        Pedido ped2 = new Pedido(null, LocalDateTime.of(2017, 10, 10, 19, 35, 0), cli1, e2);

        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
        ped1.setPagamento(pagto1);

        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, LocalDate.of(2017, 10, 20), null);
        ped2.setPagamento(pagto2);

        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
    }
}
