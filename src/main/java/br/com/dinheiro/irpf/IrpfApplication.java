package br.com.dinheiro.irpf;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.dinheiro.irpf.util.Util;

@SpringBootApplication
public class IrpfApplication {

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		SpringApplication.run(IrpfApplication.class, args);
		
		Util util = new Util();
		util.segundoPdf();
	}
}
