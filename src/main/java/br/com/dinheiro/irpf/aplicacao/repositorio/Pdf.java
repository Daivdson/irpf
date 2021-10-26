package br.com.dinheiro.irpf.aplicacao.repositorio;

import br.com.dinheiro.irpf.aplicacao.dominio.DadosPdf;

import java.util.List;

public interface Pdf {
    List<DadosPdf> extraiDadosPdf(String nomeArquivo);
}
