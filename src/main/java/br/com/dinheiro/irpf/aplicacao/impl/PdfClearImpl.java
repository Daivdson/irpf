package br.com.dinheiro.irpf.aplicacao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.dinheiro.irpf.aplicacao.api.ServicoPdfClear;
import br.com.dinheiro.irpf.aplicacao.dominio.DadosPdf;
import br.com.dinheiro.irpf.aplicacao.dominio.Negociacao;
import br.com.dinheiro.irpf.aplicacao.repositorio.Pdf;
import br.com.dinheiro.irpf.aplicacao.dominio.Operacao;

public class PdfClearImpl implements ServicoPdfClear {

	private Pdf pdf;

	public PdfClearImpl(Pdf pdf) {
		this.pdf = pdf;
	}

	@Override
	public List<Negociacao> notaNegociacao(String nomeArquivo) {
		List<DadosPdf> dados = pdf.extraiDadosPdf(nomeArquivo);
		List<String> dadosNegociacao01 = new ArrayList<>();

		String bovespa = "1-BOVESPA";
		String dataPregao = "Data pregão";//quebra
		String cliente = "Cliente";// quebra
		String totalCBLC = "Total CBLC";
		String valorLiquidoOperacoes = "Valor líquido das operações";
		String taxaLiquida = "Taxa de liquidação";
		String valorLiquidoPara = "Líquido para";



		dadosNegociacao01 = dados.get(1).getDados().stream()
				.filter(v -> v.contains(bovespa))
				.map(String::new)
				.collect(Collectors.toList());

		// paginas
			// linhas
				//operacoes e demais dados
		
		return null;
	}

}
