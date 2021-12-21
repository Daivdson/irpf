package br.com.dinheiro.irpf.aplicacao.dominio;

import br.com.dinheiro.irpf.util.Util;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
        this.taxaLiquidacao = isNull(taxaLiquidacao);
        this.taxaRegistro = isNull(taxaRegistro);
        this.taxaTempoOperacao = isNull(taxaTempoOperacao);
        this.taxaANA = isNull(taxaANA);
        this.emolumentos = isNull(emolumentos);
        this.taxaOperacional = isNull(taxaOperacional);
        this.taxaExecucao = isNull(taxaExecucao);
        this.taxaCustodia = isNull(taxaCustodia);
        this.impostos = isNull(impostos);
        this.irrf = isNull(irrf);
        this.outrasTaxas = isNull(outrasTaxas);
    }

    public BigDecimal totalTaxas() {
        return Util.somaBigDecimal(taxaLiquidacao, taxaRegistro, taxaTempoOperacao, taxaANA, emolumentos,
                taxaOperacional, taxaExecucao, taxaCustodia, impostos, irrf, outrasTaxas);
    }

    private BigDecimal isNull(BigDecimal valor) {
        return Objects.isNull(valor) ? new BigDecimal(0) : valor;
    }
}
