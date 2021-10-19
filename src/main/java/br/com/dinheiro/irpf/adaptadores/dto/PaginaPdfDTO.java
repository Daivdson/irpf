package br.com.dinheiro.irpf.adaptadores.dto;

import java.util.List;

import lombok.Data;

@Data
public class PaginaPdfDTO {
	
	public PaginaPdfDTO(List<String> linhas) {
		this.linhas = linhas;
	}

	private List<String> linhas;
}
