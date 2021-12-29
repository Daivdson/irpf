package br.com.dinheiro.irpf.aplicacao.dominio;

import org.junit.Test;

import java.math.BigDecimal;
import static org.junit.Assert.*;

public class TaxasTest {

    @Test
    public void taxasSeVazioNaoDeveRetornoNull() {
        Taxas taxaVazia = Taxas.builder().build();
        assertEquals(BigDecimal.ZERO, taxaVazia.totalTaxas());
    }

    @Test
    public void somaDasTaxas() {
        Taxas taxa = Taxas.builder()
                .taxaLiquidacao(new BigDecimal("1.0"))
                .taxaANA(new BigDecimal("2.12"))
                .taxaRegistro(new BigDecimal("1.5"))
                .build();
        assertEquals(new BigDecimal("4.62"), taxa.totalTaxas());
    }
}
