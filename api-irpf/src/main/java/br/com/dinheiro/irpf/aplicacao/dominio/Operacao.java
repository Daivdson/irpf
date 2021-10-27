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
	private String observacao;
	private LocalDateTime data;
	
	@Builder
	public Operacao(String tipoNegociacao, int quantidade, BigDecimal precoAcao, BigDecimal valorOperacao, String tipoOperacao, Acao acao, String observacao, LocalDateTime data) {
		this.tipoNegociacao = tipoNegociacao;
		this.quantidade = quantidade;
		this.precoAcao = precoAcao;
		this.valorOperacao = valorOperacao;
		this.tipoOperacao = tipoOperacao;
		this.acao = acao;
		this.observacao = observacao;
		this.data = data;
	}
	

}
