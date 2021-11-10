package br.com.dinheiro.irpf.aplicacao.impl;

import lombok.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class NegociacaoDTO {
    private String nomeCliente;
    private String cpf;
    private String idCliente;
    private String dataNegociacao;
    private String taxaLiquidacao;
    private String emonumentos;
    private String irrf;
    private List<OperacaoDto> operacao;
    private String numeroNota;
    private Map<String, Object> dadosNegociacaoSemTratamento;


    /*public NegociacaoDTO(List<OperacaoDto> operacao, String nomeCliente, String cpf, String idCliente,
                         String dataNegociacao, String taxaLiquidacao, String emonumentos, String irrf, String numeroNota) {

        this.operacao = operacao;
        this.nomeCliente = nomeCliente;
        this.cpf = cpf;
        this.idCliente = idCliente;
        this.dataNegociacao = dataNegociacao;
        this.taxaLiquidacao = taxaLiquidacao;
        this.emonumentos = emonumentos;
        this.irrf = irrf;
        this.numeroNota = numeroNota;

    }*/


    public NegociacaoDTO(List<OperacaoDto> operacao, String nomeCliente, String cpf, String idCliente,
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
        negociacaojson.put("operacoes", operacao);

        this.dadosNegociacaoSemTratamento = negociacaojson;
    }
}


