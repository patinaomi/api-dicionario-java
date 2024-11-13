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

