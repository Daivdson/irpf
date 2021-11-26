package br.com.dinheiro.irpf.aplicacao.impl.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class NegociacaoDTO {
    private String nomeCliente;
    private String cpf;
    private String idCliente;
    private String dataNegociacao;
    private String taxaLiquidacao;
    private String emolumentos;
    private String irrf;
    private List<OperacaoDto> operacao;
    private String numeroNota;


    public NegociacaoDTO(List<OperacaoDto> operacao, String nomeCliente, String cpf, String idCliente,
                         String dataNegociacao, String taxaLiquidacao, String emolumentos, String irrf, String numeroNota) {

        this.operacao = operacao;
        this.nomeCliente = nomeCliente;
        this.cpf = cpf;
        this.idCliente = idCliente;
        this.dataNegociacao = dataNegociacao;
        this.taxaLiquidacao = taxaLiquidacao;
        this.emolumentos = emolumentos;
        this.irrf = irrf;
        this.numeroNota = numeroNota;

    }

}


