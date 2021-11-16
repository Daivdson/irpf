package br.com.dinheiro.irpf.aplicacao.dominio;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Negociacao {
    private List<Operacao> operacao;
    private BigDecimal irrf;
    private BigDecimal totalCompra;
    private BigDecimal totalVenda;
    private Date dataNegociacao;
    private boolean teveDayTrade;
    private BigDecimal emolumentos;
    private BigDecimal taxaLiquidacao;
    //NegociacaoDto ainda não está colhendo as informações abaixo
    //Apenas a taxa de liquidação e emolumentos
    //private BigDecimal totalComTaxas;
    //private BigDecimal totalSemTaxas;

    @Builder
    public Negociacao(List<Operacao> operacao, BigDecimal irrf,
                      Date dataNegociacao, BigDecimal emolumentos, BigDecimal taxaLiquidacao) {

        this.operacao = operacao;
        this.irrf = irrf;
        this.totalCompra = calcularTotalCompra(operacao);
        this.totalVenda = calcularTotalVenda(operacao);
        this.dataNegociacao = dataNegociacao;
        this.teveDayTrade = verificarSeTeveDayTrade(operacao);
        this.emolumentos = emolumentos;
        this.taxaLiquidacao = taxaLiquidacao;
        //this.totalComTaxas = totalComTaxas;
        //this.totalSemTaxas = totalSemTaxas;
    }

    private BigDecimal calcularTotalCompra(List<Operacao> operacao){

        BigDecimal soma = operacao.stream().filter(t -> t.getTipoOperacao() == TipoOperacao.COMPRA.getOperacao())
                .map(valor -> valor.getValorOperacao()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return soma;

    }
    private BigDecimal calcularTotalVenda(List<Operacao> operacao){

        BigDecimal soma = operacao.stream().filter(t -> t.getTipoOperacao() == TipoOperacao.VENDA.getOperacao())
                .map(valor -> valor.getValorOperacao()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return soma;
    }

    private boolean verificarSeTeveDayTrade(List<Operacao> operacoes){
        List<Operacao> operacoesCompra = operacoes.stream().
                filter(operacao -> operacao.getTipoOperacao() == TipoOperacao.COMPRA.getOperacao()).collect(Collectors.toList());
        List<Operacao> operacoesVenda = operacoes.stream().
                filter(operacao -> operacao.getTipoOperacao() == TipoOperacao.VENDA.getOperacao()).collect(Collectors.toList());

        return operacoesCompra.stream().
                anyMatch(opCompra -> operacoesVenda.stream().
                        anyMatch(opVenda -> opVenda.getAcao().getNome().
                                equals(opCompra.getAcao().getNome())));
    }


}

