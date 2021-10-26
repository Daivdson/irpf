package br.com.dinheiro.irpf.adaptadores.pdfBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.dinheiro.irpf.aplicacao.dominio.PaginaPdf;
import br.com.dinheiro.irpf.aplicacao.repositorio.Pdf;
import br.com.dinheiro.irpf.config.propriedades.PropriedadeDiretorio;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.google.common.collect.Lists;

import br.com.dinheiro.irpf.adaptadores.pdfBox.dto.PaginaPdfDTO;

public class ExtraiPdf implements Pdf {

	private PropriedadeDiretorio diretorio;

	public ExtraiPdf(PropriedadeDiretorio diretorio) {
		this.diretorio = diretorio;
	}

	private PDDocument lerPdf(String nomeArquivo) {
		String caminhoArquivos = diretorio.getCaminho() + "/";
		System.out.println(caminhoArquivos);
		File file = new File(caminhoArquivos + nomeArquivo);
		
		try {
			PDFParser parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
			parser.parse();
			COSDocument documento = parser.getDocument();
			
			PDDocument pdf = new PDDocument(documento);
			//documento.close();
			return pdf;
		} catch (IOException e) {
			throw new RuntimeException("Não foi possível extrair dados do PDF", e);
		}
	}

	@Override
	public List<PaginaPdf> extraiPaginasPdf(String nomeArquivo) {
		try {
			PDDocument documento = lerPdf(nomeArquivo);
			
			PDFTextStripper extraiDados = new PDFTextStripper();

			List<PaginaPdf> paginas = new ArrayList<PaginaPdf>();

			for (int i = 0; documento.getNumberOfPages() >= i; i++) {
				extraiDados.setStartPage(i);
				extraiDados.setEndPage(i);
				String paginaEmLinhas[] = extraiDados.getText(documento).split("\\r?\\n");
				paginas.add(PaginaPdf.builder().linhas(Lists.newArrayList(paginaEmLinhas)).build());
			}
			documento.close();

			return paginas;
		} catch (IOException e) {
			throw new RuntimeException("Não foi possível extrair linhas do PDF", e);
		}
	}

}
