package br.com.dinheiro.irpf.aplicacao.dominio;

import org.junit.Test;

import java.math.BigDecimal;
import static org.junit.Assert.*;

public class OperacaoTest {

    @Test
    public void valoresDeOperacaoCorreto() {
        BigDecimal valorDeComparacao = new BigDecimal("971.70");
        Operacao operacao = Operacao.builder()
                .tipoNegociacao("1-BOVESPA")
                .quantidade(82)
                .precoAcao(BigDecimal.valueOf(11.85))
                .valorOperacao(BigDecimal.valueOf(971.7))
                .tipoOperacao(TipoOperacao.COMPRA)
                .acao(new Acao("DURATEX","ON"))
                .build();

        assertEquals(0 , operacao.getValorOperacao().compareTo(valorDeComparacao));

        Operacao operacao2 = Operacao.builder()
                .tipoNegociacao("1-BOVESPA")
                .quantidade(82)
                .precoAcao(BigDecimal.valueOf(11.85))
                .tipoOperacao(TipoOperacao.COMPRA)
                .acao(new Acao("DURATEX","ON"))
                .build();

        assertEquals(0 , operacao2.getValorOperacao().compareTo(valorDeComparacao));
    }

    @Test
    public void dadosDivergentesDaOperacao() {
        assertThrows(IllegalArgumentException.class, () -> Operacao.builder()
                .tipoNegociacao("1-BOVESPA")
                .quantidade(91)
                .precoAcao(BigDecimal.valueOf(10.23))
                .valorOperacao(BigDecimal.valueOf(1255.15))
                .tipoOperacao(TipoOperacao.VENDA)
                .acao(new Acao("POSITIVO","ON"))
                .build());

        assertThrows(IllegalArgumentException.class, () -> Operacao.builder()
                .tipoNegociacao("1-BOVESPA")
                .quantidade(18)
                .precoAcao(BigDecimal.valueOf(42.22))
                .valorOperacao(BigDecimal.valueOf(760.14))
                .tipoOperacao(TipoOperacao.COMPRA)
                .acao(new Acao("WEG","ON"))
                .build());
    }

    @Test
    public void semDadosBasicoDaOperacao() {
        assertThrows(IllegalArgumentException.class, () -> Operacao.builder()
                .tipoNegociacao("1-BOVESPA")
                .precoAcao(BigDecimal.valueOf(10.23))
                .valorOperacao(BigDecimal.valueOf(1255.15))
                .tipoOperacao(TipoOperacao.VENDA)
                .acao(new Acao("POSITIVO","ON"))
                .build());

        assertThrows(IllegalArgumentException.class, () -> Operacao.builder()
                .tipoNegociacao("1-BOVESPA")
                .quantidade(18)
                .tipoOperacao(TipoOperacao.COMPRA)
                .acao(new Acao("WEG","ON"))
                .build());
    }
}
