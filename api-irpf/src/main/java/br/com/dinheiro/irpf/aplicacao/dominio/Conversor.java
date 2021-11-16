package br.com.dinheiro.irpf.aplicacao.dominio;

import br.com.dinheiro.irpf.aplicacao.impl.NegociacaoDTO;
import br.com.dinheiro.irpf.aplicacao.impl.OperacaoDto;

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
        Negociacao negociacao = new Negociacao(
                converterDtoOperacoes(negociacaoDTO.getOperacao()),
                converterStringParaBigDecimal(negociacaoDTO.getIrrf()),
                formatarData(negociacaoDTO.getDataNegociacao()),
                converterStringParaBigDecimal(negociacaoDTO.getEmolumentos()),
                converterStringParaBigDecimal(negociacaoDTO.getTaxaLiquidacao()));

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
                obterTipoOperacao(operacaoDto.getTipoOperacao()),
                converterDtoAcao(operacaoDto));
        return operacao;
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

    private char obterTipoOperacao(String entrada){
        char saida = entrada.charAt(0);
        if(saida == TipoOperacao.COMPRA.getOperacao()){
            return TipoOperacao.COMPRA.getOperacao();
        }
        return TipoOperacao.VENDA.getOperacao();
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
}


