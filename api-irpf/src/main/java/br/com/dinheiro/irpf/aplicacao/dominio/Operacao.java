package br.com.dinheiro.irpf.aplicacao.dominio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class Operacao {
	private String tipoNegociacao;
	private int quantidade;
	private BigDecimal precoAcao;
	private BigDecimal valorOperacao;
	private TipoOperacao tipoOperacao;
	private Acao acao;
	
	@Builder
	public Operacao(String tipoNegociacao, int quantidade, BigDecimal precoAcao, BigDecimal valorOperacao, TipoOperacao tipoOperacao, Acao acao) {
		this.tipoNegociacao = tipoNegociacao;
		this.quantidade = quantidade;
		this.precoAcao = precoAcao;
		this.tipoOperacao = tipoOperacao;
		if(Objects.isNull(quantidade) || Objects.isNull(precoAcao) || Objects.isNull(tipoOperacao)) {
			throw new IllegalArgumentException("Preço, quantidade e tipoOperacao não podem ser null ou vazio");
		}
		this.valorOperacao = Objects.isNull(valorOperacao) ? calculaValorOperacao() : analisaValorOperacao(valorOperacao);
		this.acao = acao;
	}

	private BigDecimal analisaValorOperacao(BigDecimal valorOperacao) {
		if(valorOperacao.compareTo(calculaValorOperacao()) != 0)
			throw new IllegalArgumentException("Valore da operação esta divergente. Quantidade, preço e valor da operação devem bater.");
		return valorOperacao;
	}

	private BigDecimal calculaValorOperacao() {
		return precoAcao.multiply(new BigDecimal(quantidade));
	}


}
