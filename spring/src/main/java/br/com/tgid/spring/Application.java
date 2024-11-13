package br.com.tgid.spring;

import br.com.tgid.spring.domains.Categoria;
import br.com.tgid.spring.domains.Cidade;
import br.com.tgid.spring.domains.Estado;
import br.com.tgid.spring.domains.Produto;
import br.com.tgid.spring.repositories.CategoriaRepository;
import br.com.tgid.spring.repositories.CidadeRepository;
import br.com.tgid.spring.repositories.EstadoRespository;
import br.com.tgid.spring.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@RequiredArgsConstructor
@SpringBootApplication
public class Application implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final EstadoRespository estadoRespository;
    private final CidadeRepository cidadeRepository;

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

    }
}
