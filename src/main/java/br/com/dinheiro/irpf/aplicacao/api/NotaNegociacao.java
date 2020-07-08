package br.com.dinheiro.irpf.aplicacao.api;

import java.util.List;

import br.com.dinheiro.irpf.aplicacao.dominio.Operacao;

public interface NotaNegociacao {

	List<Operacao> operacoes(String nomeDoArquivo);
}
