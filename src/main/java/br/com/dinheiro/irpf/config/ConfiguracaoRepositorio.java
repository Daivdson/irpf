package br.com.dinheiro.irpf.config;

import br.com.dinheiro.irpf.adaptadores.pdfBox.ExtraiPdf;
import br.com.dinheiro.irpf.adaptadores.pdfBox.ExtraiPdfImpl;
import br.com.dinheiro.irpf.aplicacao.RepositorioPdf;
import br.com.dinheiro.irpf.config.propriedades.PropriedadeDiretorio;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PropriedadeDiretorio.class)
public class ConfiguracaoRepositorio {




    @Bean
    public RepositorioPdf repositorioPdf(PropriedadeDiretorio propriedadeDiretorio) {
        return new ExtraiPdfImpl(new ExtraiPdf(propriedadeDiretorio));
    }
}
