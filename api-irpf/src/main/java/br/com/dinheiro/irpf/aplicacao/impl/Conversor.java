package br.com.dinheiro.irpf.aplicacao.impl;

import br.com.dinheiro.irpf.aplicacao.dominio.Acao;
import br.com.dinheiro.irpf.aplicacao.dominio.Negociacao;
import br.com.dinheiro.irpf.aplicacao.dominio.Operacao;
import br.com.dinheiro.irpf.aplicacao.dominio.TipoOperacao;
import br.com.dinheiro.irpf.aplicacao.impl.dto.NegociacaoDTO;
import br.com.dinheiro.irpf.aplicacao.impl.dto.OperacaoDto;
import br.com.dinheiro.irpf.util.Util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Conversor {

    public List<Negociacao> converterDtoNegociacoes(List<NegociacaoDTO> negociacoesDTO) {
        return negociacoesDTO.stream()
                .map(this::converterDtoNegociacao)
                .collect(Collectors.toList());
    }

    public  Negociacao converterDtoNegociacao(NegociacaoDTO negociacaoDTO){
        return Negociacao.builder()
                .nomeCliente(negociacaoDTO.getNomeCliente())
                .cpf(negociacaoDTO.getCpf())
                .idCliente(negociacaoDTO.getIdCliente())
                .operacao(converterDtoOperacoes(negociacaoDTO.getOperacao()))
                .irrf(converterStringParaBigDecimal(negociacaoDTO.getIrrf()))
                .dataNegociacao(Util.formatarData(negociacaoDTO.getDataNegociacao()))
                .emolumentos(converterStringParaBigDecimal(negociacaoDTO.getEmolumentos()))
                .taxaLiquidacao(converterStringParaBigDecimal(negociacaoDTO.getTaxaLiquidacao()))
                .build();
    }

    private List<Operacao> converterDtoOperacoes(List<OperacaoDto> operacoesDto){
        return operacoesDto.stream().
                map(this::converterDtoOperacao)
                .collect(Collectors.toList());
    }

    private Operacao converterDtoOperacao(OperacaoDto operacaoDto){
        return Operacao.builder()
                .tipoNegociacao(operacaoDto.getTipoNegociacao())
                .quantidade(Integer.parseInt(operacaoDto.getQuantidade()))
                .precoAcao(converterStringParaBigDecimal(operacaoDto.getPreco()))
                .valorOperacao(converterStringParaBigDecimal(operacaoDto.getValorOperacao()))
                .tipoOperacao(TipoOperacao.porAbreviacao(operacaoDto.getTipoOperacao()))
                .acao(converterDtoAcao(operacaoDto))
                .build();
    }

    private Acao converterDtoAcao(OperacaoDto operacaoDto){
        Acao acao = new Acao(operacaoDto.getAtivo(),
                operacaoDto.getTipoAcao());
        return acao;
    }

    private BigDecimal converterStringParaBigDecimal(String entrada){
        //TODO verificar se notas de corretagem acima de 1000 reais aparece ponto
        String numeroComPonto = entrada.replaceAll(",",".");
        return new BigDecimal(numeroComPonto);
    }

    private Integer converterStringParaInt(String entrada){
        return Integer.parseInt(entrada);
    }

}


