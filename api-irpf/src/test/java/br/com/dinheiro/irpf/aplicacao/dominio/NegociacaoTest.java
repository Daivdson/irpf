package br.com.dinheiro.irpf.aplicacao.dominio;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;


public class NegociacaoTest {
    private Operacao operacaoDeCompra1;
    private Operacao operacaoDeCompra2;
    private Operacao operacaoDeCompra3;
    private Operacao operacaoDeVenda1;
    private Operacao operacaoDeVenda2;
    private Operacao operacaoDeVenda3;
    private Taxas taxas;

    @Before
    public void inicio() {
        taxas = Taxas.builder()
                .taxaRegistro(BigDecimal.valueOf(0.15))
                .taxaLiquidacao(BigDecimal.valueOf(1.0))
                .taxaANA(BigDecimal.valueOf(1.05))
                .irrf(BigDecimal.valueOf(1.49))
                .build();
        operacaoDeCompra1 = Operacao.builder()
                .tipoNegociacao("1-BOVESPA")
                .quantidade(82)
                .precoAcao(BigDecimal.valueOf(11.85))
                .valorOperacao(BigDecimal.valueOf(971.7))
                .tipoOperacao(TipoOperacao.COMPRA)
                .acao(new Acao("DURATEX","ON"))
                .build();

        operacaoDeCompra2 = Operacao.builder()
                .tipoNegociacao("1-BOVESPA")
                .quantidade(32)
                .precoAcao(BigDecimal.valueOf(11.67))
                .valorOperacao(BigDecimal.valueOf(373.44))
                .tipoOperacao(TipoOperacao.COMPRA)
                .acao(new Acao("VALID","ON"))
                .build();

        operacaoDeCompra3 = Operacao.builder()
                .tipoNegociacao("1-BOVESPA")
                .quantidade(18)
                .precoAcao(BigDecimal.valueOf(42.23))
                .valorOperacao(BigDecimal.valueOf(760.14))
                .tipoOperacao(TipoOperacao.COMPRA)
                .acao(new Acao("WEG","ON"))
                .build();

        operacaoDeVenda1 = Operacao.builder()
                .tipoNegociacao("1-BOVESPA")
                .quantidade(30)
                .precoAcao(BigDecimal.valueOf(12.15))
                .valorOperacao(BigDecimal.valueOf(364.5))
                .tipoOperacao(TipoOperacao.VENDA)
                .acao(new Acao("DURATEX","ON"))
                .build();

        operacaoDeVenda2= Operacao.builder()
                .tipoNegociacao("1-BOVESPA")
                .quantidade(45)
                .precoAcao(BigDecimal.valueOf(11.67))
                .valorOperacao(BigDecimal.valueOf(525.15))
                .tipoOperacao(TipoOperacao.VENDA)
                .acao(new Acao("VIAVAREJO","ON"))
                .build();

        operacaoDeVenda3 = Operacao.builder()
                .tipoNegociacao("1-BOVESPA")
                .quantidade(105)
                .precoAcao(BigDecimal.valueOf(12.23))
                .valorOperacao(BigDecimal.valueOf(1284.15))
                .tipoOperacao(TipoOperacao.VENDA)
                .acao(new Acao("POSITIVO","ON"))
                .build();
    }

    @Test
    public void totalDeCompraEstaBatendo() {
        Negociacao negociacao1 = Negociacao.builder()
                .operacao(Arrays.asList(operacaoDeCompra1,operacaoDeCompra2,operacaoDeCompra3))
                .nomeCliente("Dados basicos")
                .cpf("Dados basicos")
                .idCliente("Dados basicos")
                .taxa(taxas)
                .build();
        assertEquals(new BigDecimal("2105.28"), negociacao1.getTotalCompra());

        Negociacao negociacao2 = Negociacao.builder()
                .operacao(Arrays.asList(operacaoDeVenda3,operacaoDeVenda1))
                .nomeCliente("Dados basicos")
                .cpf("Dados basicos")
                .idCliente("Dados basicos")
                .taxa(taxas)
                .build();
        assertEquals(BigDecimal.ZERO, negociacao2.getTotalCompra());

        Negociacao negociacao3 = Negociacao.builder()
                .operacao(Arrays.asList(operacaoDeCompra1,operacaoDeCompra2,operacaoDeCompra3, operacaoDeVenda1))
                .nomeCliente("Dados basicos")
                .cpf("Dados basicos")
                .idCliente("Dados basicos")
                .taxa(taxas)
                .build();
        assertEquals(new BigDecimal("2105.28"), negociacao3.getTotalCompra());
    }

    @Test
    public void totalDeVendaEstaBatendo() {
        Negociacao negociacao1 = Negociacao.builder()
                .operacao(Arrays.asList(operacaoDeVenda1,operacaoDeVenda2,operacaoDeVenda3))
                .nomeCliente("Dados basicos")
                .cpf("Dados basicos")
                .idCliente("Dados basicos")
                .taxa(taxas)
                .build();
        assertEquals(new BigDecimal("2173.80"), negociacao1.getTotalVenda());

        Negociacao negociacao2 = Negociacao.builder()
                .operacao(Arrays.asList(operacaoDeCompra3,operacaoDeCompra2,operacaoDeCompra1))
                .nomeCliente("Dados basicos")
                .cpf("Dados basicos")
                .idCliente("Dados basicos")
                .taxa(taxas)
                .build();
        assertEquals(BigDecimal.ZERO, negociacao2.getTotalVenda());
    }

    @Test
    public void totalLiquidoDasOperacoesEstaBatendo() {
        Negociacao negociacao1 = Negociacao.builder()
                .operacao(Arrays.asList(operacaoDeVenda1,operacaoDeCompra2,operacaoDeCompra1))
                .nomeCliente("Dados basicos")
                .cpf("Dados basicos")
                .idCliente("Dados basicos")
                .taxa(taxas)
                .build();
        assertEquals(new BigDecimal("984.33"), negociacao1.getTotalLiquidoDasOperacoes());

        Negociacao negociacao2 = Negociacao.builder()
                .operacao(Arrays.asList(operacaoDeVenda1,operacaoDeCompra2,operacaoDeCompra1,operacaoDeVenda1))
                .nomeCliente("Dados basicos")
                .cpf("Dados basicos")
                .idCliente("Dados basicos")
                .taxa(taxas)
                .build();
        assertEquals(new BigDecimal("619.83"), negociacao2.getTotalLiquidoDasOperacoes());
    }

    @Test
    public void negociacaoComOperacoesDayTradeDeAcao() {
        Negociacao negociacaoComDayTrade = Negociacao.builder()
                .operacao(Arrays.asList(operacaoDeCompra1,operacaoDeCompra2,operacaoDeVenda1))
                .nomeCliente("Dados basicos")
                .cpf("Dados basicos")
                .idCliente("Dados basicos")
                .build();
        assertEquals("Essa operação espera que exista um day trade",true, negociacaoComDayTrade.isTeveDayTrade());

    }

    @Test
    public void negociacaoComTaxasZeradas() {
        Negociacao negociacao = Negociacao.builder()
                .operacao(Arrays.asList(operacaoDeCompra1))
                .nomeCliente("Dados basicos")
                .cpf("Dados basicos")
                .idCliente("Dados basicos")
                .build();
        assertEquals("Total de taxas deve ser a somatória de todas as taxas", BigDecimal.ZERO, negociacao.getTaxas().totalTaxas());
    }

    @Test
    public void dadosBasicoDoClienteDeveLancarExcecaoSeNull() {

        assertThrows(NullPointerException.class, () -> Negociacao.builder()
                .operacao(Arrays.asList(operacaoDeCompra1))
                .idCliente("322121")
                .cpf("045.6589.523-78")
                .build());

        assertThrows(NullPointerException.class, () -> Negociacao.builder()
                .operacao(Arrays.asList(operacaoDeCompra1))
                .nomeCliente("Marcos de Almeida Ferreira")
                .idCliente("322121")
                .build());

        assertThrows(NullPointerException.class, () -> Negociacao.builder()
                .operacao(Arrays.asList(operacaoDeCompra1))
                .nomeCliente("João de Almeida Nascimento")
                .cpf("045.6589.523-78")
                .build());
    }

}
