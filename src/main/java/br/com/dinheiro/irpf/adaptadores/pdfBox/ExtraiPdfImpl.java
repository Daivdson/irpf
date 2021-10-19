package br.com.dinheiro.irpf.adaptadores.pdfBox;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.dinheiro.irpf.adaptadores.dto.PaginaPdfDTO;
import br.com.dinheiro.irpf.aplicacao.RepositorioPdf;
import br.com.dinheiro.irpf.aplicacao.dominio.Operacao;
import org.springframework.beans.factory.annotation.Autowired;

public class ExtraiPdfImpl implements RepositorioPdf {

	private ExtraiPdf pdf;

	public ExtraiPdfImpl(ExtraiPdf pdf) {
		this.pdf = pdf;
	}

	@Override
	public List<Operacao> notaNegociacao(String nomeArquivo) {
		List<PaginaPdfDTO> paginas = pdf.extraiLinhasPdf(nomeArquivo);
		List<String> operacoesPagina01 = new ArrayList<>();

		String bovespa = "1-BOVESPA";
		String dataPregao = "Data pregão";//quebra
		String cliente = "Cliente";// quebra
		String totalCBLC = "Total CBLC";
		String valorLiquidoOperacoes = "Valor líquido das operações";
		String taxaLiquida = "Taxa de liquidação";
		String valorLiquidoPara = "Líquido para";



		operacoesPagina01 = paginas.get(1).getLinhas().stream()
				.filter(v -> v.contains(bovespa))
				.map(String::new)
				.collect(Collectors.toList());

		// paginas
			// linhas
				//operacoes e demais dados
		
		return null;
	}

}
