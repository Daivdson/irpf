package br.com.dinheiro.irpf.aplicacao.dominio;

import br.com.dinheiro.irpf.util.Util;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Negociacao {
    private List<Operacao> operacao;
    private String nomeCliente;
    private String cpf;
    private String idCliente;
    private BigDecimal irrf;
    private BigDecimal totalCompra;
    private BigDecimal totalVenda;
    private Date dataNegociacao;
    private boolean teveDayTrade;
    private BigDecimal emolumentos;
    private BigDecimal taxaLiquidacao;
    private BigDecimal totalLiquidoDasOperacoes;
    private BigDecimal totalDeTaxas;
    // TODO add todas as outras taxas informada na nota mesmo que zerados

    @Builder
    public Negociacao(List<Operacao> operacao, BigDecimal irrf, Date dataNegociacao, BigDecimal emolumentos,
                      BigDecimal taxaLiquidacao, String nomeCliente, String cpf, String idCliente) {
        this.nomeCliente = nomeCliente;
        this.cpf = cpf;
        this.idCliente = idCliente;
        this.operacao = operacao;
        this.irrf = irrf;
        this.totalCompra = calcularTotalCompra(operacao);
        this.totalVenda = calcularTotalVenda(operacao);
        this.dataNegociacao = dataNegociacao;
        this.teveDayTrade = verificarSeTeveDayTrade(operacao);
        this.emolumentos = emolumentos;
        this.taxaLiquidacao = taxaLiquidacao;
        this.totalDeTaxas = calcularTotalDeTaxas();
        this.totalLiquidoDasOperacoes = calcularTotalLiquidoDasOperacoes();
    }

    private BigDecimal calcularTotalDeTaxas() {
        return Util.somaBigDecimal(irrf, taxaLiquidacao,emolumentos);
    }

    private BigDecimal calcularTotalLiquidoDasOperacoes() {
        BigDecimal valorQuididoDeVenda = this.totalVenda.subtract(this.totalDeTaxas);
        return this.totalCompra.subtract(valorQuididoDeVenda);
    }

    private BigDecimal calcularTotalCompra(List<Operacao> operacao){
        BigDecimal soma = operacao.stream().filter(t -> t.getTipoOperacao() == TipoOperacao.COMPRA)
                .map(valor -> valor.getValorOperacao()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return soma;
    }

    private BigDecimal calcularTotalVenda(List<Operacao> operacao){
        BigDecimal soma = operacao.stream().filter(t -> t.getTipoOperacao() == TipoOperacao.VENDA)
                .map(valor -> valor.getValorOperacao()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return soma;
    }

    private boolean verificarSeTeveDayTrade(List<Operacao> operacoes){
        List<Operacao> operacoesCompra = operacoes.stream().
                filter(operacao -> operacao.getTipoOperacao() == TipoOperacao.COMPRA).collect(Collectors.toList());
        List<Operacao> operacoesVenda = operacoes.stream().
                filter(operacao -> operacao.getTipoOperacao() == TipoOperacao.VENDA).collect(Collectors.toList());

        return operacoesCompra.stream().
                anyMatch(opCompra -> operacoesVenda.stream().
                        anyMatch(opVenda -> opVenda.getAcao().getNome().
                                equals(opCompra.getAcao().getNome())));
    }
}

