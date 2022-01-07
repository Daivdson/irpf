package br.com.dinheiro.irpf.aplicacao.impl.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class NegociacaoDTO {
    private String nomeCliente;
    private String cpf;
    private String idCliente;
    private String dataNegociacao;
    private List<OperacaoDto> operacao;
    private String numeroNota;

    private String taxaLiquidacao;
    private String taxaRegistro;
    private String taxaTempoOperacao;
    private String taxaANA;
    private String emolumentos;
    private String taxaOperacional;
    private String taxaExecucao;
    private String taxaCustodia;
    private String impostos;
    private String irrf;
    private String outrasTaxas;

    public NegociacaoDTO(String nomeCliente, String cpf, String idCliente, String dataNegociacao, List<OperacaoDto> operacao,
                         String numeroNota, String taxaLiquidacao, String taxaRegistro, String taxaTempoOperacao, String taxaANA,
                         String emolumentos, String taxaOperacional, String taxaExecucao, String taxaCustodia, String impostos, String irrf, String outrasTaxas) {

        this.nomeCliente = nomeCliente;
        this.cpf = cpf;
        this.idCliente = idCliente;
        this.dataNegociacao = dataNegociacao;
        this.operacao = operacao;
        this.numeroNota = numeroNota;
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

}


