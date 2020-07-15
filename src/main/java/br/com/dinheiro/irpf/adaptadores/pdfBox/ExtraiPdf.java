package br.com.dinheiro.irpf.adaptadores.pdfBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.env.Environment;

import com.google.common.collect.Lists;

import br.com.dinheiro.irpf.adaptadores.dto.PaginaPdfDTO;

public class ExtraiPdf {

	private Environment env;

	public ExtraiPdf(Environment env) {
		super();
		this.env = env;
	}

	private PDDocument lerPdf(String nomeArquivo) {
		String caminhoArquivos = env.getProperty("diretorio") + "/"; 
		
		File file = new File(caminhoArquivos + nomeArquivo);
		
		try {
			PDFParser parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
			parser.parse();
			COSDocument documento = parser.getDocument();
			
			PDDocument pdf = new PDDocument(documento);
			documento.close();
			return pdf;
		} catch (IOException e) {
			throw new RuntimeException("Não foi possível extrair dados do PDF", e);
		}

	}

	public List<PaginaPdfDTO> extraiLinhasPdf(String nomeArquivo) {
		try {
			PDDocument documento = lerPdf(nomeArquivo);
			
			PDFTextStripper extraiDados = new PDFTextStripper();

			List<PaginaPdfDTO> paginas = new ArrayList<PaginaPdfDTO>();
			

			for (int i = 0; documento.getNumberOfPages() >= i; i++) {
				extraiDados.setStartPage(i);
				extraiDados.setEndPage(i);
				String paginaEmLinhas[] = extraiDados.getText(documento).split("\\r?\\n");
				paginas.add(new PaginaPdfDTO(Lists.newArrayList(paginaEmLinhas)));
			}
			documento.close();
			return paginas;
		} catch (IOException e) {
			throw new RuntimeException("Não foi possível extrair linhas do PDF", e);
		}

	}

}
