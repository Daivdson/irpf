package br.com.dinheiro.irpf.aplicacao.dominio;

import lombok.Builder;
import lombok.Getter;


@Getter
public class Negociacao {
    private Operacao operacao;
    private double irrf;
    private double valorLiquido;
    private double totalLiquido;


    @Builder
    public Negociacao(Operacao operacao, double irrf, double valorLiquido, double totalLiquido){
        this.operacao = operacao;
        this.irrf = irrf;
        this.valorLiquido = valorLiquido;
        this.totalLiquido = totalLiquido;
    }
}
