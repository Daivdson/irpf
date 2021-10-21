package br.com.dinheiro.irpf.aplicacao.dominio;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PaginaPdf {

    @Builder
    public PaginaPdf(List<String> linhas) {
        this.linhas = linhas;
    }

    private List<String> linhas;

}
