package br.com.dinheiro.irpf.config;

import br.com.dinheiro.irpf.aplicacao.RepositorioPdf;
import br.com.dinheiro.irpf.aplicacao.api.ServicoNotaNegociacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracaoServico {

    @Autowired
    private RepositorioPdf pdf;

    @Bean
    public ServicoNotaNegociacao servicoNotaNegociacao() {
        return new ServicoNotaNegociacao(pdf);
    }
}
