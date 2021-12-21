package br.com.dinheiro.irpf.aplicacao.dominio;

import br.com.dinheiro.irpf.util.Util;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
    private Taxas taxas;

    @Builder
    public Negociacao(List<Operacao> operacao, Date dataNegociacao,
                      @NonNull String nomeCliente, @NonNull String cpf, @NonNull String idCliente, Taxas taxa) {
        this.nomeCliente = nomeCliente;
        this.cpf = cpf;
        this.idCliente = idCliente;
        this.operacao = operacao;
        this.dataNegociacao = Objects.isNull(dataNegociacao) ? new Date() : dataNegociacao;
        this.taxas = Objects.isNull(taxa) ? Taxas.builder().build() : taxa;
        this.totalCompra = calcularTotalCompra(operacao);
        this.totalVenda = calcularTotalVenda(operacao);
        this.teveDayTrade = verificarSeTeveDayTrade(operacao);
        this.totalLiquidoDasOperacoes = calcularTotalLiquidoDasOperacoes();
    }

    private @NonNull BigDecimal calcularTotalLiquidoDasOperacoes() {
          return this.totalCompra.subtract(calculaValorLiquidoDeVenda()) ;
    }

    private @NonNull BigDecimal calculaValorLiquidoDeVenda() {
          return this.totalVenda.subtract(taxas.totalTaxas()) ;
    }

    private BigDecimal calcularTotalCompra(@NonNull List<Operacao> operacao){
        BigDecimal soma = operacao.stream().filter(t -> t.getTipoOperacao() == TipoOperacao.COMPRA)
                .map(valor -> valor.getValorOperacao()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return soma;
    }

    private BigDecimal calcularTotalVenda(@NonNull List<Operacao> operacao){
        BigDecimal soma = operacao.stream().filter(t -> t.getTipoOperacao() == TipoOperacao.VENDA)
                .map(valor -> valor.getValorOperacao()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return soma;
    }

    private boolean verificarSeTeveDayTrade(@NonNull List<Operacao> operacoes){
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

