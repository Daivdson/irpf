package br.com.dinheiro.irpf.aplicacao.api;


import br.com.dinheiro.irpf.aplicacao.dominio.Negociacao;

import java.util.List;

public interface ServicoPdfClear {
    List<Negociacao> notaNegociacao(String nomeArquivo);
}
