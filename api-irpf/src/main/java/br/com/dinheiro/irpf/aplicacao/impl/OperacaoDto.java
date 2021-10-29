package br.com.dinheiro.irpf.aplicacao.impl;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperacaoDto {
    private String tipoNegociacao;
    private String tipoOperacao;
    private String tipoMercado;
    private String ativo;
    private String quantidade;
    private String preco;
    private String valorOperacao;
}
