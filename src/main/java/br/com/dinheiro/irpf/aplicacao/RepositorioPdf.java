package br.com.dinheiro.irpf.aplicacao;


import br.com.dinheiro.irpf.aplicacao.dominio.Operacao;

import java.util.List;

public interface RepositorioPdf {

    List<Operacao> notaNegociacao(String arquivo);

}