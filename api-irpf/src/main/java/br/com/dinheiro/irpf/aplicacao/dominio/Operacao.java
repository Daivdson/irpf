package br.com.dinheiro.irpf.aplicacao.dominio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Operacao {
	private String tipoNegociacao;
	private int quantidade;
	private BigDecimal precoAcao;
	private BigDecimal valorOperacao;
	private String tipoOperacao;
	private Acao acao;
	
	@Builder
	public Operacao(String tipoNegociacao, int quantidade, BigDecimal precoAcao, BigDecimal valorOperacao, String tipoOperacao, Acao acao) {
		this.tipoNegociacao = tipoNegociacao;
		this.quantidade = quantidade;
		this.precoAcao = precoAcao;
		this.valorOperacao = valorOperacao;
		this.tipoOperacao = tipoOperacao;
		this.acao = acao;
	}
	

}
