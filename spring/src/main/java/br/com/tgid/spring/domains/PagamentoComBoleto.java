package br.com.tgid.spring.domains;

import br.com.tgid.spring.domains.enums.EstadoPagamento;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class PagamentoComBoleto extends Pagamento {

    private LocalDate dataVencimento;
    private LocalDate dataPagamento;

    public PagamentoComBoleto() {

    }

    public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, LocalDate dataVencimento, LocalDate dataPagamento) {
        super(id, estado, pedido);
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
