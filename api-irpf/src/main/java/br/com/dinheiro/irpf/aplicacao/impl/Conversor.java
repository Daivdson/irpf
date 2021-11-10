package br.com.dinheiro.irpf.aplicacao.impl;


import br.com.dinheiro.irpf.aplicacao.dominio.Acao;
import br.com.dinheiro.irpf.aplicacao.dominio.Negociacao;
import br.com.dinheiro.irpf.aplicacao.dominio.Operacao;
import br.com.dinheiro.irpf.aplicacao.impl.NegociacaoDTO;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Conversor {

    public Conversor() {
    }

    public List<Negociacao> converterDtoNegociacoes(List<NegociacaoDTO> negociacoesDTO) {

        return negociacoesDTO.stream()
                .map((this::converterDtoNegociacao))
                .collect(Collectors.toList());
    }

    public  Negociacao converterDtoNegociacao(NegociacaoDTO negociacaoDTO){
        Negociacao negociacao = new Negociacao(converterDtoOperacoes(negociacaoDTO.getOperacao()),
                converterStringParaBigDecimal(negociacaoDTO.getIrrf()), formatarData(negociacaoDTO.getDataNegociacao()),
                verificarSeTeveDayTrade(converterDtoOperacoes(negociacaoDTO.getOperacao())));

        return negociacao;
    }

    private List<Operacao> converterDtoOperacoes(List<OperacaoDto> operacoesDto){

        return operacoesDto.stream().
                map(this::converterDtoOperacao)
                .collect(Collectors.toList());
    }

    private Operacao converterDtoOperacao(OperacaoDto operacaoDto){

        Operacao operacao = new Operacao(operacaoDto.getTipoNegociacao(),
                converterStringParaInt(operacaoDto.getQuantidade()),
                converterStringParaBigDecimal(operacaoDto.getPreco()),
                converterStringParaBigDecimal(operacaoDto.getValorOperacao()),
                operacaoDto.getTipoOperacao(),
                converterDtoAcao(operacaoDto));
        return operacao;
    }

    private Acao converterDtoAcao(OperacaoDto operacaoDto){
        Acao acao = new Acao(operacaoDto.getAtivo(),
                operacaoDto.getTipoAcao());
        return acao;
    }

    private BigDecimal converterStringParaBigDecimal(String entrada){
        BigDecimal saida = new BigDecimal(entrada);
        return saida;
    }

    private Integer converterStringParaInt(String entrada){
        return Integer.parseInt(entrada);
    }

    private Date formatarData(String dataRecebida){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date data = null;
        try {
            data = formato.parse(dataRecebida);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    private Boolean verificarSeTeveDayTrade(List<Operacao> operacoes) {

        for(Operacao operacao : operacoes){
            for(Operacao operacaoAVerificar : operacoes){

                if(operacao.getAcao().equals(operacaoAVerificar.getAcao()) &&
                        !operacao.getTipoOperacao().equals(operacaoAVerificar.getTipoOperacao())){
                    return true;
                }
            }
        }
        return false;
    }

}
