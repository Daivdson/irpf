package br.com.dinheiro.irpf.adaptadores.pdfBox;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.dinheiro.irpf.aplicacao.dominio.PaginaPdf;
import br.com.dinheiro.irpf.aplicacao.repositorio.Pdf;
import br.com.dinheiro.irpf.config.propriedades.PropriedadeDiretorio;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

@Slf4j
public class ExtraiPdf implements Pdf{

	private PropriedadeDiretorio diretorio;

	public ExtraiPdf(PropriedadeDiretorio diretorio) {
		this.diretorio = diretorio;
	}

	private PDDocument getPDDocument(InputStream arquivo) {
		try {
			return PDDocument.load(arquivo);
		} catch (IOException e) {
			throw new RuntimeException("Não foi possível analisar arquivo", e);
		}
	}

	private String getCaminhoArquivo(String nomeArquivo) {
		return diretorio.getCaminho().concat("/").concat(nomeArquivo);
	}

	@Override
	public List<PaginaPdf> extraiPaginasPdf(String nomeArquivo) {
		PDDocument arquivoExtraido = null;
		try {
			InputStream arquivo = new FileInputStream(getCaminhoArquivo(nomeArquivo));

			arquivoExtraido = getPDDocument(arquivo);

			return extraiPaginasElinhasDoArquivo(arquivoExtraido);
		} catch (IOException e) {
			throw new RuntimeException("Não foi possível extrair linhas do PDF", e);
		}finally {
			if (arquivoExtraido != null) {
				try {
					arquivoExtraido.close();
				} catch (IOException e) {
					log.error("Não foi possível fazer o close() do PDDocument");
				}
			}
		}
	}

	private List<PaginaPdf> extraiPaginasElinhasDoArquivo(PDDocument arquivoExtraido) throws IOException {
		PDFTextStripper pdf = new PDFTextStripper();
		List<PaginaPdf> paginas = new ArrayList<>();

		for (int i = 0; arquivoExtraido.getNumberOfPages() >= i; i++) {
			pdf.setStartPage(i);
			pdf.setEndPage(i);
			List<String> linhasDaPagina = Arrays.asList(pdf.getText(arquivoExtraido).split("\\r?\\n"));
			addPagina(paginas, linhasDaPagina);
		}
		return paginas;
	}

	private void addPagina(List<PaginaPdf> paginas, List<String> linhasDaPagina) {
		if(!CollectionUtils.isEmpty(linhasDaPagina) && linhasDaPagina.size() > 1) {
			paginas.add(new PaginaPdf(linhasDaPagina));
		}
	}

}
