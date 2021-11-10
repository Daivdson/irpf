package br.com.dinheiro.irpf.aplicacao.impl;


import br.com.dinheiro.irpf.aplicacao.dominio.Acao;
import br.com.dinheiro.irpf.aplicacao.dominio.Negociacao;
import br.com.dinheiro.irpf.aplicacao.dominio.Operacao;
import br.com.dinheiro.irpf.aplicacao.impl.NegociacaoDTO;

import java.math.BigDecimal;
import java.util.List;

public class Conversor {

    private List<NegociacaoDTO> negociacoesDTO;

    public Conversor(List<NegociacaoDTO> negociacoesDTO) {
        this.negociacoesDTO = negociacoesDTO;
    }

    public List<Negociacao> converterDtoNegociacoes(List<NegociacaoDTO> negociacoesDTO) {

        return null;
    }

    /*private  Negociacao converterDtoNegociacao(NegociacaoDTO negociacaoDTO){
        Negociacao negociacao = new Negociacao();
    }

    private Operacao converterDtoOperacao(OperacaoDto operacaoDto){
        Operacao operacao = new Operacao(operacaoDto.getTipoNegociacao(),
                converterStringParaInt(operacaoDto.getQuantidade()),
                converterStringParaBigDecimal(operacaoDto.getPreco()),
                converterStringParaBigDecimal(operacaoDto.getValorOperacao()),
                operacaoDto.getTipoOperacao(), );
        return operacao;
    }

    private Acao converterDtoAcao(OperacaoDto operacaoDto){
        Acao acao = new Acao(operacaoDto.getAtivo(),
                operacaoDto.get);
    }*/

    private BigDecimal converterStringParaBigDecimal(String entrada){
        BigDecimal saida = new BigDecimal(entrada);
        return saida;
    }

    private Integer converterStringParaInt(String entrada){
        return Integer.parseInt(entrada);
    }

}
