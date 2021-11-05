package br.com.dinheiro.irpf.aplicacao.dominio;

import br.com.dinheiro.irpf.aplicacao.impl.OperacaoDto;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Negociacao {
    private List<Operacao> operacao;
    private BigDecimal irrf;
    private BigDecimal totalCompra;
    private BigDecimal totalVenda;
    private LocalDateTime dataNegociacao;
    private boolean teveDayTrade;
    private BigDecimal totalComTaxas;
    private BigDecimal totalSemTaxas;
    private BigDecimal totalTaxas;
    private Map<String, Object> dadosNegociacaoSemTratamento;

    @Builder
    public Negociacao(List<Operacao> operacao, BigDecimal irrf, BigDecimal totalCompra, BigDecimal totalVenda,
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

    public Negociacao(List<OperacaoDto> operacao, String nomeCliente, String cpf, String idCliente,
                      String dataNegociacao, String taxaLiquidacao, String emonumentos, String irrf, String numeroNota) {

        Map<String, Object> negociacaojson = new HashMap<>();
        negociacaojson.put("nomeCliente", nomeCliente);
        negociacaojson.put("cpf", cpf);
        negociacaojson.put("idCliente", idCliente);
        negociacaojson.put("dataNegociacao", dataNegociacao);
        negociacaojson.put("taxaLiquidacao", taxaLiquidacao);
        negociacaojson.put("emonumentos", emonumentos);
        negociacaojson.put("irrf", irrf);
        negociacaojson.put("numeroNota", numeroNota);
        negociacaojson.put("operacoes", operacao );
        
        this.dadosNegociacaoSemTratamento = negociacaojson;
    }

    private static BigDecimal calcularTaxa(BigDecimal totalComTaxas, BigDecimal totalSemTaxas) {
        return totalComTaxas.subtract(totalSemTaxas);
    }

    private static BigDecimal calcularTotalCompra(List<Operacao> operacao){

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

