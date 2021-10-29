package br.com.dinheiro.irpf.aplicacao.api;

import br.com.dinheiro.irpf.aplicacao.dominio.Operacao;

import java.util.List;

public interface ServicoPdfClear {
    String notaNegociacao(String nomeArquivo);
}
