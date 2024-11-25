package br.com.tgid.spring.domains.enums;

public enum EstadoPagamento {

    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private int cod;
    private String descricao;


    EstadoPagamento(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static EstadoPagamento toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (EstadoPagamento x : EstadoPagamento.values()) { // Itera sobre os valores da enum
            if (cod.equals(x.getCod())) { // Compara o cod passado com o cod de cada enum
                return x; // Retorna a instância da enum que corresponde ao cod
            }
        }
        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
