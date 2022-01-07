package br.com.dinheiro.irpf.aplicacao.dominio;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Operacao {
	private int quantidade;
	private double preco;
	private double valor;
	private String tipoOperacao;
	private Acao acao;
	private String observacao;
	private LocalDateTime data;
	
	@Builder
	public Operacao(int quantidade, double preco, double valor, String tipoOperacao, Acao acao, String observacao, LocalDateTime data) {
		this.quantidade = quantidade;
		this.preco = preco;
		this.valor = valor;
		this.tipoOperacao = tipoOperacao;
		this.acao = acao;
		this.observacao = observacao;
		this.data = data;
	}
	

}
