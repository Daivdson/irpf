package br.com.dinheiro.irpf.aplicacao.api;

import br.com.dinheiro.irpf.aplicacao.RepositorioPdf;
import br.com.dinheiro.irpf.aplicacao.dominio.Operacao;

import java.util.List;

public class ServicoNotaNegociacao {
    private RepositorioPdf pdf;

    public ServicoNotaNegociacao(RepositorioPdf pdf) {
        this.pdf = pdf;
    }

    public List<Operacao> notasDeNegociacao(String nomeArquivo) {
        pdf.notaNegociacao(nomeArquivo);
        return null;
    }

}
