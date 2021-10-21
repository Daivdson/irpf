package br.com.dinheiro.irpf.config.propriedades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties("diretorio")
public class PropriedadeDiretorio {
    private String caminho;
}
