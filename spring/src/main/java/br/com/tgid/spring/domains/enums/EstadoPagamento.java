package br.com.tgid.spring.domains.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoPagamento {

    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private int cod;
    private String descricao;

    public static EstadoPagamento toEnum(Integer cod) {
        if(cod == null) {
            return null;
        }

        for(EstadoPagamento x : EstadoPagamento.values()) { // Itera sobre os valores da enum
            if(cod.equals(x.getCod())) { // Compara o cod passado com o cod de cada enum
                return x; // Retorna a instância da enum que corresponde ao cod
            }
        }
        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
