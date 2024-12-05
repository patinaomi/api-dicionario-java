package br.com.tgid.spring.service;

import br.com.tgid.spring.domains.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BoletoService {

    public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, LocalDateTime instante) {
        // Adiciona 7 dias ao instante fornecido
        LocalDateTime vencimento = instante.plusDays(7);
        pagto.setDataVencimento(vencimento.toLocalDate());
    }
}

