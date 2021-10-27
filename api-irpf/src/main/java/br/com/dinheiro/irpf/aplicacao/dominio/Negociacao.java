package br.com.dinheiro.irpf.aplicacao.dominio;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Getter
public class Negociacao {
    private ArrayList<Operacao> operacao;
    private BigDecimal irrf;
    private BigDecimal totalCompra;
    private BigDecimal totalVenda;
    private LocalDateTime dataNegociacao;
    private boolean teveDayTrade;
    private BigDecimal totalComTaxas;
    private BigDecimal totalSemTaxas;
    private BigDecimal totalTaxas;


    @Builder
    public Negociacao(ArrayList<Operacao> operacao, BigDecimal irrf, BigDecimal totalCompra, BigDecimal totalVenda,
                      LocalDateTime dataNegociacao, boolean teveDayTrade, BigDecimal totalComTaxas,
                      BigDecimal totalSemTaxas) {

        this.operacao = operacao;
        this.irrf = irrf;
        this.totalCompra = totalCompra;
        this.totalVenda = totalVenda;
        this.dataNegociacao = dataNegociacao;
        this.teveDayTrade = teveDayTrade;
        this.totalComTaxas = totalComTaxas;
        this.totalSemTaxas = totalSemTaxas;
        this.totalTaxas = calcularTaxa(totalComTaxas,totalSemTaxas);
    }

    private static BigDecimal calcularTaxa(BigDecimal totalComTaxas, BigDecimal totalSemTaxas) {
        return totalComTaxas.subtract(totalSemTaxas);
    }

    private static BigDecimal calcularTotalCompra(ArrayList<Operacao> operacao){

        BigDecimal soma = new BigDecimal("0.0");

        for(Operacao o : operacao) {
            if (o != null) {
                if (o.getTipoOperacao().equalsIgnoreCase("C")) {
                    soma.add(o.getValorOperacao());
                }
            }
        }
        return soma;
    }
}

