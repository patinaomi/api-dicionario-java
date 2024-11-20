# Revisão Conteúdo Spring

## Arquitetura de Projeto
### Domains
As domains são o núcleo da aplicação, representa as regras de negócio e lógica principal. É onde ficam as "entidades" que descrevem os principais conceitos do sistema. Exemplo: Cliente, Pedido, Produto etc.

#### Mapeamento das entidades
1. **@OneToOne** - Uma entidade está associada a apenas uma outra entidade e vice-versa.
Ex: Um usuário tem um único perfil e cada perfil pertence a apenas um usuário.

```
@OneToOne
@JoinColumn(name = "perfil_id")
private Perfil perfil;
```

2. **@OneToMany** - Uma entidade está associada a várias instâncias de outra entidade, mas cada instância dessa outra entidade pertence a apenas uma instância da primeira.
Ex: Um Estado pode ter muitos Enderecos, mas cada Endereco pertence a um único estado.
``` 
@OneToMany(mappedBy = "estado") //Tem que colocar o nome da Classe e não o da tabela!
private List<Endereco> enderecos;
```
3. **@ManyToOne** - Um relacionamento onde muitas instâncias de uma entidade podem estar associadas a uma única instância de outra entidade.
Ex: Vários Endereços podem estar associados a um único Estado.
```
@ ManyToOne
@JoinColumn(name = "estado_id)
private Estado estado;
```
4. **@ManyToMany -** Várias instâncias de uma entidade podem estar associadas a várias instâncias de outra entidade. 
```
@ManyToMany
@JoinTable(name = "produto_categoria",
           joinColumns = @JoinColumn(name = "produto_id"),
           inverseJoinColumns = @JoinColumn(name = "categoria_id"))
private List<Categoria> categorias;
```
**MappedBy**  -  Colocamos no lado não proprietário, ou seja no lado que não possui chave estrangeira. 

---
## Bibliotecas
### Lombok
O Lombok serve para reduzir verbosidade de código boilerplate utilizando anotações para gerar automaticamente construtores, getters, setters, equals, hashcode, toString, etc.

Data - gera construtores, equals, hashcode
All Args Constructor - Gera um construtor com todos os parâmetros
No Args Constructor - Gera um consrutor sem os parâmetros
Required Args Constructor - Escolher quais campos serão passados no construtor

    import lombok.RequiredArgsConstructor;
    
    @RequiredArgsConstructor
    public class Exemplo {
        private final String nome; // Lombok incluirá esse campo no construtor gerado
        private int idade; // Não será incluído no construtor
    }

Equals and HashCode - Usado quando queremos comparar classes por valor. Ver sempre quais campos precisam ser incluídos para evitar problemas de performance.


    import lombok.EqualsAndHashCode;
    
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    public class Pessoa {
        @EqualsAndHashCode.Include
        private String nome;
    
        private int idade;
    }

### Mapper
**`@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)`**: Configura o MapStruct para gerar uma implementação que será gerenciada pelo Spring, permitindo a injeção de `CategoriaMapper` como um bean do Spring.

```@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)  
public interface CategoriaMapper {  
  
  Categoria map(CategoriaRequest request);  
  CategoriaResponse map(Categoria categoria);  
  
} 
```

**Métodos `map`**:

-   `Categoria map(CategoriaRequest request)`: Mapeia de `CategoriaRequest` para `Categoria`, ideal para criação ou atualização.
-   `CategoriaResponse map(Categoria categoria)`: Mapeia de `Categoria` para `CategoriaResponse`, ideal para retornar dados ao cliente da API.
- 
### Como Utilizar o Serviço de Log

Geralmente, o logging em Java é feito usando uma biblioteca como **SLF4J** com **Logback** ou **Log4j**, ou, em algumas aplicações, utilizando diretamente o serviço de log fornecido pelo framework, como o `Logger` no Spring Boot.

1.  **Injetando o Logger**: No Spring, você pode usar a anotação `@Slf4j` (de `Lombok`) para criar automaticamente um logger. Se não estiver usando Lombok, pode criar uma instância de logger manualmente.

### Padrão de Arquitetura e Padrão de Projeto

-   **Padrão de Arquitetura** é um nível mais alto de organização do sistema. Ele define como os diferentes componentes do sistema interagem entre si e estabelece a estrutura geral do software. Exemplos incluem arquiteturas como **MVC (Model-View-Controller)**, **Microservices**, **Monolítica**, e **Clean Architecture**. O foco é na estrutura global do sistema e em como ele se conecta e se organiza para suportar requisitos funcionais e não funcionais.
    
-   **Padrão de Projeto** (ou Design Pattern) é um nível mais específico e trata de soluções para problemas recorrentes no código em si. Ele ajuda na organização do código e na criação de componentes reutilizáveis, mas não define a estrutura global do sistema. Exemplos clássicos incluem **Singleton**, **Factory**, **Observer**, e **Builder**. O foco está em resolver problemas específicos de implementação e organização do código.
A anotação `@Inheritance(strategy = InheritanceType.JOINED)` em JPA é usada para definir uma **estratégia de herança** entre classes de entidades no banco de dados. Essa anotação é aplicada em uma classe de entidade pai (ou superclasse), indicando que suas subclasses serão armazenadas em tabelas separadas, mas ligadas por uma relação de chave estrangeira.

### Como Funciona a Estratégia `JOINED`

Com `InheritanceType.JOINED`, o JPA armazena:

-   A entidade pai em uma tabela.
-   Cada subclasse em uma tabela separada.

As tabelas das subclasses têm uma **chave estrangeira** que referencia a chave primária da tabela da entidade pai. Isso permite que o JPA construa e recupere as subclasses com um **JOIN** entre as tabelas.

### Exemplo de Uso

Suponha que você tenha uma hierarquia de classes onde `Pagamento` é a classe pai, e `PagamentoComCartao` e `PagamentoComBoleto` são subclasses.

#### Classe Pai `Pagamento`

java

Copiar código

`import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pagamento {

    @Id
    private Long id;
    private Double valor;

    // Getters e Setters
}` 

#### Subclasse `PagamentoComCartao`

java

Copiar código

`import jakarta.persistence.Entity;

@Entity
public class PagamentoComCartao extends Pagamento {

    private Integer numeroDeParcelas;

    // Getters e Setters
}` 

#### Subclasse `PagamentoComBoleto`

java

Copiar código

`import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
public class PagamentoComBoleto extends Pagamento {

    private LocalDate dataVencimento;
    private LocalDate dataPagamento;

    // Getters e Setters
}` 

### Estrutura de Tabelas no Banco de Dados

Usando `@Inheritance(strategy = InheritanceType.JOINED)`, o JPA cria três tabelas:

1.  **Tabela `Pagamento`** (entidade pai):
    
    -   Colunas: `id`, `valor`
2.  **Tabela `PagamentoComCartao`** (subclasse):
    
    -   Colunas: `id`, `numeroDeParcelas`
    -   `id` é uma chave estrangeira para a tabela `Pagamento`.
3.  **Tabela `PagamentoComBoleto`** (subclasse):
    
    -   Colunas: `id`, `dataVencimento`, `dataPagamento`
    -   `id` é uma chave estrangeira para a tabela `Pagamento`.

### Vantagens e Desvantagens

#### Vantagens

-   **Normalização de Dados**: Evita a redundância de dados, pois os atributos comuns ficam na tabela pai.
-   **Flexibilidade para Consultas**: Permite consultar apenas as colunas da entidade pai (`Pagamento`), ou as colunas da subclasse específica (`PagamentoComCartao` ou `PagamentoComBoleto`) usando JOINs.

#### Desvantagens

-   **Performance**: Requer JOINs para buscar subclasses, o que pode afetar a performance em consultas complexas.
-   **Complexidade de Design**: Estruturas de JOIN podem ser complexas em heranças muito profundas.

### Quando Usar `InheritanceType.JOINED`

Use `InheritanceType.JOINED` quando você quer **evitar a duplicação de dados** entre subclasses e **preservar a normalização** no banco de dados. É útil quando as subclasses possuem dados distintos e você precisa de uma estrutura de banco bem organizada.

A anotação `@Inheritance(strategy = InheritanceType.JOINED)` em JPA é usada para definir uma **estratégia de herança** entre classes de entidades no banco de dados. Essa anotação é aplicada em uma classe de entidade pai (ou superclasse), indicando que suas subclasses serão armazenadas em tabelas separadas, mas ligadas por uma relação de chave estrangeira.

### Como Funciona a Estratégia `JOINED`

Com `InheritanceType.JOINED`, o JPA armazena:

-   A entidade pai em uma tabela.
-   Cada subclasse em uma tabela separada.

As tabelas das subclasses têm uma **chave estrangeira** que referencia a chave primária da tabela da entidade pai. Isso permite que o JPA construa e recupere as subclasses com um **JOIN** entre as tabelas.

### Exemplo de Uso

Suponha que você tenha uma hierarquia de classes onde `Pagamento` é a classe pai, e `PagamentoComCartao` e `PagamentoComBoleto` são subclasses.

#### Classe Pai `Pagamento`

java

Copiar código

`import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pagamento {

    @Id
    private Long id;
    private Double valor;

    // Getters e Setters
}` 

#### Subclasse `PagamentoComCartao`

java

Copiar código

`import jakarta.persistence.Entity;

@Entity
public class PagamentoComCartao extends Pagamento {

    private Integer numeroDeParcelas;

    // Getters e Setters
}` 

#### Subclasse `PagamentoComBoleto`

java

Copiar código

`import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
public class PagamentoComBoleto extends Pagamento {

    private LocalDate dataVencimento;
    private LocalDate dataPagamento;

    // Getters e Setters
}` 

### Estrutura de Tabelas no Banco de Dados

Usando `@Inheritance(strategy = InheritanceType.JOINED)`, o JPA cria três tabelas:

1.  **Tabela `Pagamento`** (entidade pai):
    
    -   Colunas: `id`, `valor`
2.  **Tabela `PagamentoComCartao`** (subclasse):
    
    -   Colunas: `id`, `numeroDeParcelas`
    -   `id` é uma chave estrangeira para a tabela `Pagamento`.
3.  **Tabela `PagamentoComBoleto`** (subclasse):
    
    -   Colunas: `id`, `dataVencimento`, `dataPagamento`
    -   `id` é uma chave estrangeira para a tabela `Pagamento`.

### Vantagens e Desvantagens

#### Vantagens

-   **Normalização de Dados**: Evita a redundância de dados, pois os atributos comuns ficam na tabela pai.
-   **Flexibilidade para Consultas**: Permite consultar apenas as colunas da entidade pai (`Pagamento`), ou as colunas da subclasse específica (`PagamentoComCartao` ou `PagamentoComBoleto`) usando JOINs.

#### Desvantagens

-   **Performance**: Requer JOINs para buscar subclasses, o que pode afetar a performance em consultas complexas.
-   **Complexidade de Design**: Estruturas de JOIN podem ser complexas em heranças muito profundas.

### Quando Usar `InheritanceType.JOINED`

Use `InheritanceType.JOINED` quando você quer **evitar a duplicação de dados** entre subclasses e **preservar a normalização** no banco de dados. É útil quando as subclasses possuem dados distintos e você precisa de uma estrutura de banco bem organizada.

4o

##### Você disse:

@OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido") e esse cascade?

###### O ChatGPT disse:

ChatGPT

A anotação `@OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")` configura um relacionamento de **um-para-um** em JPA, onde a entidade anotada possui uma relação direta com outra entidade. O `cascade = CascadeType.ALL` significa que todas as operações (como persistência, atualização e remoção) feitas em uma entidade também serão realizadas na entidade associada. Vou explicar cada parte em detalhes:

### Explicação dos Atributos

1.  **`@OneToOne`**: Define um relacionamento de um-para-um entre duas entidades.
    
2.  **`cascade = CascadeType.ALL`**:
    
    -   O atributo `cascade` controla o comportamento de propagação das operações entre as entidades associadas.
    -   Com `CascadeType.ALL`, qualquer operação feita na entidade principal será automaticamente aplicada à entidade associada. Isso inclui:
        -   **`PERSIST`**: Ao salvar a entidade principal, a entidade relacionada também será salva.
        -   **`MERGE`**: Ao atualizar a entidade principal, a entidade relacionada também será atualizada.
        -   **`REMOVE`**: Ao remover a entidade principal, a entidade relacionada também será removida.
        -   **`REFRESH`**: Ao atualizar a entidade principal a partir do banco, a entidade relacionada também será atualizada.
        -   **`DETACH`**: Ao separar a entidade principal do contexto de persistência, a entidade relacionada também será separada.
3.  **`mappedBy = "pedido"`**:
    
    -   O atributo `mappedBy` indica que este lado do relacionamento é o **lado inverso** ou **não proprietário**.
    -   `pedido` refere-se ao nome do campo na entidade oposta que é o proprietário do relacionamento. Nesse caso, a entidade associada tem um campo chamado `pedido`, que é o lado proprietário do relacionamento.

### Exemplo de Uso

Vamos considerar um exemplo com as entidades `Pedido` e `Pagamento`, onde um `Pedido` possui um `Pagamento` associado.

#### Classe `Pedido`

java

Copiar código

`import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Pedido {

    @Id
    private Long id;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Pagamento pagamento;

    // Getters e Setters
}` 

#### Classe `Pagamento`

java

Copiar código

`import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Pagamento {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id") // Define a chave estrangeira na tabela Pagamento
    private Pedido pedido;

    // Getters e Setters
}` 

### O Que `CascadeType.ALL` Faz Neste Contexto?

1.  **Ao Salvar um Pedido (`Pedido`)**:
    
    -   Se `Pagamento` estiver associado ao `Pedido`, ele também será salvo automaticamente, sem a necessidade de salvá-lo explicitamente.
2.  **Ao Excluir um Pedido**:
    
    -   O `Pagamento` associado será excluído automaticamente.
3.  **Ao Atualizar um Pedido**:
    
    -   Qualquer alteração no `Pagamento` associado também será automaticamente refletida.

### Vantagens e Cuidados

-   **Vantagens**: O `CascadeType.ALL` simplifica operações em relacionamentos, garantindo que as entidades relacionadas sejam automaticamente atualizadas, removidas ou persistidas conforme necessário.
-   **Cuidados**: Use `CascadeType.ALL` apenas quando fizer sentido que as operações de uma entidade sejam refletidas na outra. Em relacionamentos onde a entidade relacionada precisa ser independente, evite esse tipo de cascata, pois pode causar exclusões acidentais.

Em resumo, `CascadeType.ALL` garante que todas as operações realizadas na entidade principal também afetem a entidade associada, o que é útil em relacionamentos onde as entidades estão intimamente ligadas.

-   **`@Embeddable`** é ideal para representar **tipos de dados compostos** ou **grupos de atributos relacionados**.
-   A classe anotada com `@Embeddable` é incorporada em outra entidade usando a anotação `@Embedded`.

