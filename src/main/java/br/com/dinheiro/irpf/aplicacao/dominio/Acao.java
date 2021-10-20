package br.com.dinheiro.irpf.aplicacao.dominio;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Acao {
	private String nome;
	private String codigo;
	private String tipo;
	
	@Builder
	public Acao(String nome, String codigo, String tipo) {
		this.nome = nome;
		this.codigo = codigo;
		this.tipo = tipo;
	}
	
}
