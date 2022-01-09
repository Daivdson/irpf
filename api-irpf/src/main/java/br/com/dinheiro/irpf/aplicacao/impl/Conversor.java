package br.com.dinheiro.irpf.aplicacao.impl;

import br.com.dinheiro.irpf.aplicacao.dominio.*;
import br.com.dinheiro.irpf.aplicacao.impl.dto.NegociacaoDTO;
import br.com.dinheiro.irpf.aplicacao.impl.dto.OperacaoDto;
import br.com.dinheiro.irpf.util.Util;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.dinheiro.irpf.util.Util.*;

public class Conversor {

    public List<Negociacao> converterDtoNegociacoes(List<NegociacaoDTO> negociacoesDTO) {
        return negociacoesDTO.stream()
                .map(this::converterDtoNegociacao)
                .collect(Collectors.toList());
    }

    public Negociacao converterDtoNegociacao(NegociacaoDTO negociacaoDTO){
        return Negociacao.builder()
                .nomeCliente(negociacaoDTO.getNomeCliente())
                .cpf(negociacaoDTO.getCpf())
                .idCliente(negociacaoDTO.getIdCliente())
                .operacao(converterDtoOperacoes(negociacaoDTO.getOperacao()))
                .dataNegociacao(formatarData(negociacaoDTO.getDataNegociacao()))
                .taxa(getTaxas(negociacaoDTO))
                .build();
    }

    private Taxas getTaxas(NegociacaoDTO dto) {
        return Taxas.builder()
                .emolumentos(stringParaBigDecimalTendoVirgulaComoDecimal(dto.getEmolumentos()))
                .impostos(stringParaBigDecimalTendoVirgulaComoDecimal(dto.getImpostos()))
                .irrf(stringParaBigDecimalTendoVirgulaComoDecimal(dto.getIrrf()))
                .outrasTaxas(stringParaBigDecimalTendoVirgulaComoDecimal(dto.getOutrasTaxas()))
                .taxaANA(stringParaBigDecimalTendoVirgulaComoDecimal(dto.getTaxaANA()))
                .taxaCustodia(stringParaBigDecimalTendoVirgulaComoDecimal(dto.getTaxaCustodia()))
                .taxaExecucao(stringParaBigDecimalTendoVirgulaComoDecimal(dto.getTaxaExecucao()))
                .taxaLiquidacao(stringParaBigDecimalTendoVirgulaComoDecimal(dto.getTaxaLiquidacao()))
                .taxaOperacional(stringParaBigDecimalTendoVirgulaComoDecimal(dto.getTaxaOperacional()))
                .taxaRegistro(stringParaBigDecimalTendoVirgulaComoDecimal(dto.getTaxaRegistro()))
                .taxaTempoOperacao(stringParaBigDecimalTendoVirgulaComoDecimal(dto.getTaxaTempoOperacao()))
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
                .precoAcao(stringParaBigDecimalTendoVirgulaComoDecimal(operacaoDto.getPreco()))
                .valorOperacao(stringParaBigDecimalTendoVirgulaComoDecimal(operacaoDto.getValorOperacao()))
                .tipoOperacao(TipoOperacao.porAbreviacao(operacaoDto.getTipoOperacao()))
                .acao(converterDtoAcao(operacaoDto))
                .build();
    }

    private Acao converterDtoAcao(OperacaoDto operacaoDto){
        Acao acao = new Acao(operacaoDto.getAtivo(),
                operacaoDto.getTipoAcao());
        return acao;
    }

    private Integer converterStringParaInt(String entrada){
        return Integer.parseInt(entrada);
    }
}


