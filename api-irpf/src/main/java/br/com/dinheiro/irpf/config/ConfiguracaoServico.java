package br.com.dinheiro.irpf.config;

import br.com.dinheiro.irpf.aplicacao.api.ServicoPdfClear;
import br.com.dinheiro.irpf.aplicacao.impl.PdfClearImpl;
import br.com.dinheiro.irpf.aplicacao.repositorio.Pdf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracaoServico {

    @Bean
    public ServicoPdfClear servicoPdfClear(Pdf pdf) {
        return new PdfClearImpl(pdf);
    }
}
