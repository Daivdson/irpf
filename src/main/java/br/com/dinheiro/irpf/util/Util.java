package br.com.dinheiro.irpf.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class Util {

	private String nomeArquivo = "./arquivos-upload/nota-junho.pdf";
	

	private String path = "" + nomeArquivo;

	
	public void primeiroPdf() throws InvalidPasswordException, IOException {

		File file = new File(path);

		try (PDDocument document = PDDocument.load(file)) {

			if (!document.isEncrypted()) {

				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);

				PDFTextStripper tStripper = new PDFTextStripper();

				
				String pdfFileInText = tStripper.getText(document);
				
				System.out.println("Pagina: "+document.getNumberOfPages());
				System.out.println(pdfFileInText);
				String lines[] = pdfFileInText.split("\\r?\\n");
				for (String line : lines) {
					//System.out.println(line);
				}
			}
		}
	}

	public void segundoPdf() {
		File file = new File(path);
		try {
			PDFParser parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
			parser.parse();
			COSDocument cosDoc = parser.getDocument();
			
			PDDocument documento = new PDDocument(cosDoc);
			
			PDFTextStripper extraiDados = new PDFTextStripper();
			
			String dadosDoc = extraiDados.getText(documento);
			
			//cosDoc.close();
			//documento.close();
			
			
//			List<String> linhas = Lists.newArrayList(dadosDoc.split("\\r?\\n"));
			
//			linhas.forEach(v -> System.out.println(v));
			
			
//			 List<String> novasLinhas = linhas.stream()
//				.filter(v -> v.contains("1-BOVESPA"))
//				.collect(Collectors.toList());
			 
			 
			 for (int i = 0; documento.getNumberOfPages() >= i; i++) {
				 extraiDados.setStartPage(i);
				 extraiDados.setEndPage(i);
				String paginaEmLinhas =  extraiDados.getText(documento);
				List<String> linhas = Lists.newArrayList(paginaEmLinhas.split("\\r?\\n"));
				linhas.forEach(v -> System.out.println(v));
				System.out.println("***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ********** ***** ***** ***** ***** *****");
			}
		} catch (IOException e) {
			// Tratar a exceção adequadamente.
			e.printStackTrace();
		}
	}

}
