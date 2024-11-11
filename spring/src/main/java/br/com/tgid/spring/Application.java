package br.com.tgid.spring;

import br.com.tgid.spring.domains.Categoria;
import br.com.tgid.spring.domains.Produto;
import br.com.tgid.spring.repositories.CategoriaRepository;
import br.com.tgid.spring.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class Application implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;

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


    }
}
