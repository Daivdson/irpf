package br.com.dinheiro.irpf.aplicacao.impl;

import br.com.dinheiro.irpf.aplicacao.dominio.*;
import br.com.dinheiro.irpf.aplicacao.impl.dto.NegociacaoDTO;
import br.com.dinheiro.irpf.aplicacao.impl.dto.OperacaoDto;
import br.com.dinheiro.irpf.util.Util;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
                .dataNegociacao(Util.formatarData(negociacaoDTO.getDataNegociacao()))
                .taxa(getTaxas(negociacaoDTO))
                .build();
    }

    private Taxas getTaxas(NegociacaoDTO dto) {
        return Taxas.builder()
                .emolumentos(stringParaBigDecimal(dto.getEmolumentos()))
                .impostos(stringParaBigDecimal(dto.getImpostos()))
                .irrf(stringParaBigDecimal(dto.getIrrf()))
                .outrasTaxas(stringParaBigDecimal(dto.getOutrasTaxas()))
                .taxaANA(stringParaBigDecimal(dto.getTaxaANA()))
                .taxaCustodia(stringParaBigDecimal(dto.getTaxaCustodia()))
                .taxaExecucao(stringParaBigDecimal(dto.getTaxaExecucao()))
                .taxaLiquidacao(stringParaBigDecimal(dto.getTaxaLiquidacao()))
                .taxaOperacional(stringParaBigDecimal(dto.getTaxaOperacional()))
                .taxaRegistro(stringParaBigDecimal(dto.getTaxaRegistro()))
                .taxaTempoOperacao(stringParaBigDecimal(dto.getTaxaTempoOperacao()))
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
                .precoAcao(stringParaBigDecimal(operacaoDto.getPreco()))
                .valorOperacao(stringParaBigDecimal(operacaoDto.getValorOperacao()))
                .tipoOperacao(TipoOperacao.porAbreviacao(operacaoDto.getTipoOperacao()))
                .acao(converterDtoAcao(operacaoDto))
                .build();
    }

    private Acao converterDtoAcao(OperacaoDto operacaoDto){
        Acao acao = new Acao(operacaoDto.getAtivo(),
                operacaoDto.getTipoAcao());
        return acao;
    }

    private BigDecimal stringParaBigDecimal(String entrada){
        //TODO verificar se notas de corretagem acima de 1000 reais aparece ponto
        String numeroComPonto = entrada.replaceAll(",",".");
        return new BigDecimal(numeroComPonto);
    }

    private Integer converterStringParaInt(String entrada){
        return Integer.parseInt(entrada);
    }

}


