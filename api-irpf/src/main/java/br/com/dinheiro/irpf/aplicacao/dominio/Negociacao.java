package br.com.dinheiro.irpf.aplicacao.dominio;

import br.com.dinheiro.irpf.util.Util;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

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

    private BigDecimal totalCompra;
    private BigDecimal totalVenda;
    private Date dataNegociacao;
    private boolean teveDayTrade;

    private BigDecimal totalLiquidoDasOperacoes;
    private BigDecimal totalDeTaxas;
    private Taxas taxas;

    @Builder
    public Negociacao(List<Operacao> operacao, Date dataNegociacao,
                       String nomeCliente, String cpf, String idCliente, Taxas taxa) {
        this.nomeCliente = nomeCliente;
        this.cpf = cpf;
        this.idCliente = idCliente;
        this.operacao = operacao;
        this.dataNegociacao = dataNegociacao;
        this.taxas = taxa;
        this.totalCompra = calcularTotalCompra(operacao);
        this.totalVenda = calcularTotalVenda(operacao);
        this.teveDayTrade = verificarSeTeveDayTrade(operacao);
        this.totalDeTaxas = calcularTotalDeTaxas();
        this.totalLiquidoDasOperacoes = calcularTotalLiquidoDasOperacoes();
    }

    private BigDecimal calcularTotalDeTaxas() {
        List vazio = Arrays.asList(new BigDecimal("0"));
        return Util.somaBigDecimal(ObjectUtils.isEmpty(taxas)? vazio : taxas.toList());
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

