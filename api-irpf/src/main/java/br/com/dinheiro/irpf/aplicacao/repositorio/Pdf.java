package br.com.dinheiro.irpf.aplicacao.repositorio;

import br.com.dinheiro.irpf.aplicacao.dominio.PaginaPdf;

import java.util.List;

public interface Pdf {
    List<PaginaPdf> extraiPaginasPdf(String nomeArquivo);
}
