package br.com.dinheiro.irpf;

import java.io.IOException;

import br.com.dinheiro.irpf.adaptadores.pdfBox.ExtraiPdf;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.dinheiro.irpf.util.Util;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class IrpfApplication {
	public static void main(String[] args) throws InvalidPasswordException, IOException {
		SpringApplication.run(IrpfApplication.class, args);
	}
}
