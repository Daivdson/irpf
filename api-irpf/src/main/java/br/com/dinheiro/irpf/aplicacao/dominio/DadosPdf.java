package br.com.dinheiro.irpf.aplicacao.dominio;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class DadosPdf {

    @Builder
    public DadosPdf(List<String> dados) {
        this.dados = dados;
    }

    private List<String> dados;

}
