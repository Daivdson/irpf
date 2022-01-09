package br.com.dinheiro.irpf.aplicacao.impl;

import br.com.dinheiro.irpf.aplicacao.dominio.Negociacao;
import br.com.dinheiro.irpf.aplicacao.dominio.TipoOperacao;
import br.com.dinheiro.irpf.aplicacao.impl.dto.NegociacaoDTO;
import br.com.dinheiro.irpf.aplicacao.impl.dto.OperacaoDto;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConversorTest {
    Date data = null;
    private Conversor conversor;
    NegociacaoDTO negociacaoDto;

    @Before
    public void inicio() throws ParseException {
        conversor = new Conversor();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        data = formato.parse("10/12/2021");

        negociacaoDto = NegociacaoDTO.builder()
                .nomeCliente("Jose")
                .cpf("123.123.132-12")
                .idCliente("1234")
                .dataNegociacao("10/12/2021")
                .emolumentos("12,1")
                .impostos("0,2")
                .irrf("0,23")
                .outrasTaxas("1,32")
                .taxaANA("0,23")
                .taxaTempoOperacao("0,65")
                .operacao(Arrays.asList(OperacaoDto.builder()
                        .tipoNegociacao("1-BOVESPA")
                        .quantidade("82")
                        .preco("11,85")
                        .valorOperacao("971,7")
                        .tipoOperacao("C")
                        .build()))
                .build();
    }

    @Test
    public void conversaoDeDadosBasicosDeNegociacao() {
        Negociacao negociacao = conversor.converterDtoNegociacao(negociacaoDto);

        assertEquals(negociacao.getDataNegociacao(), data);
        assertEquals(negociacao.getNomeCliente(), "Jose");
        assertEquals(negociacao.getCpf(), "123.123.132-12");
        assertEquals(negociacao.getIdCliente(), "1234");
    }

    @Test
    public void conversaoDeTaxas() {
        Negociacao negociacao = conversor.converterDtoNegociacao(negociacaoDto);
        assertEquals(negociacao.getTaxas().getEmolumentos(), BigDecimal.valueOf(12.1));
        assertEquals(negociacao.getTaxas().getImpostos(), BigDecimal.valueOf(0.2));
        assertEquals(negociacao.getTaxas().getIrrf(), BigDecimal.valueOf(0.23));
        assertEquals(negociacao.getTaxas().getOutrasTaxas(), BigDecimal.valueOf(1.32));
        assertEquals(negociacao.getTaxas().getTaxaANA(), BigDecimal.valueOf(0.23));
        assertEquals(negociacao.getTaxas().getTaxaTempoOperacao(), BigDecimal.valueOf(0.65));
    }

    @Test
    public void conversaoDeOperacoes() {
        Negociacao negociacao = conversor.converterDtoNegociacao(negociacaoDto);
        assertEquals(negociacao.getOperacao().get(0).getValorOperacao(), BigDecimal.valueOf(971.7));
        assertEquals(negociacao.getOperacao().get(0).getQuantidade(), 82);
        assertEquals(negociacao.getOperacao().get(0).getPrecoAcao(), BigDecimal.valueOf(11.85));
        assertEquals(negociacao.getOperacao().get(0).getTipoOperacao(), TipoOperacao.COMPRA);
        assertEquals(negociacao.getOperacao().get(0).getTipoNegociacao(), "1-BOVESPA");
    }
}
