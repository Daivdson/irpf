package br.com.dinheiro.irpf.aplicacao.dominio;

import br.com.dinheiro.irpf.aplicacao.impl.OperacaoDto;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class Negociacao {
    private List<Operacao> operacao;
    private BigDecimal irrf;
    private BigDecimal totalCompra;
    private BigDecimal totalVenda;
    private Date dataNegociacao;
    private boolean teveDayTrade;
    //private BigDecimal totalComTaxas; -> É necessário que o DTO colha esta informação da nota.
    //private BigDecimal totalSemTaxas; -> É necessário que o DTO colha esta informação da nota.
    //private BigDecimal totalTaxas;


    @Builder
    public Negociacao(List<Operacao> operacao, BigDecimal irrf,
                      Date dataNegociacao, boolean teveDayTrade) {

        this.operacao = operacao;
        this.irrf = irrf;
        this.totalCompra = calcularTotalCompra(operacao);
        this.totalVenda = calcularTotalVenda(operacao);
        this.dataNegociacao = dataNegociacao;
        this.teveDayTrade = teveDayTrade;
        //this.totalComTaxas = totalComTaxas;
        //this.totalSemTaxas = totalSemTaxas;
        //this.totalTaxas = calcularTaxa(totalComTaxas,totalSemTaxas);
    }

    //Este método somente será aplicado quando o DTO colher os dados do total da nota
   private static BigDecimal calcularTaxa(BigDecimal totalComTaxas, BigDecimal totalSemTaxas) {
        return totalComTaxas.subtract(totalSemTaxas);
    }

    private static BigDecimal calcularTotalCompra(List<Operacao> operacao){

        BigDecimal soma = operacao.stream().filter(t -> t.getTipoOperacao().equalsIgnoreCase("C"))
                .map(valor -> valor.getValorOperacao()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return soma;

    }
    private static BigDecimal calcularTotalVenda(List<Operacao> operacao){

        BigDecimal soma = operacao.stream().filter(t -> t.getTipoOperacao().equalsIgnoreCase("V"))
                .map(valor -> valor.getValorOperacao()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return soma;
    }


}

