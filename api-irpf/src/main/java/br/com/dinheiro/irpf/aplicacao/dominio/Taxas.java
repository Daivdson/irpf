package br.com.dinheiro.irpf.aplicacao.dominio;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Getter
public class Taxas {

    private BigDecimal taxaLiquidacao;
    private BigDecimal taxaRegistro;
    private BigDecimal taxaTempoOperacao;
    private BigDecimal taxaANA;
    private BigDecimal emolumentos;

    private BigDecimal taxaOperacional;
    private BigDecimal taxaExecucao;
    private BigDecimal taxaCustodia;
    private BigDecimal impostos;
    private BigDecimal irrf;
    private BigDecimal outrasTaxas;

    @Builder
    public Taxas(BigDecimal taxaLiquidacao, BigDecimal taxaRegistro, BigDecimal taxaTempoOperacao, BigDecimal taxaANA, BigDecimal emolumentos, BigDecimal taxaOperacional, BigDecimal taxaExecucao, BigDecimal taxaCustodia, BigDecimal impostos, BigDecimal irrf, BigDecimal outrasTaxas) {
        this.taxaLiquidacao = taxaLiquidacao;
        this.taxaRegistro = taxaRegistro;
        this.taxaTempoOperacao = taxaTempoOperacao;
        this.taxaANA = taxaANA;
        this.emolumentos = emolumentos;
        this.taxaOperacional = taxaOperacional;
        this.taxaExecucao = taxaExecucao;
        this.taxaCustodia = taxaCustodia;
        this.impostos = impostos;
        this.irrf = irrf;
        this.outrasTaxas = outrasTaxas;
    }

    public List<BigDecimal> toList() {
        return Arrays.asList(taxaLiquidacao, taxaRegistro, taxaTempoOperacao, taxaANA, emolumentos,
                taxaOperacional, taxaExecucao, taxaCustodia, impostos, irrf, outrasTaxas);
    }
}
