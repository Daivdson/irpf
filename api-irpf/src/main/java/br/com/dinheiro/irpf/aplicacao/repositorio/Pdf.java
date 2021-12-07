package br.com.dinheiro.irpf.aplicacao.repositorio;

import br.com.dinheiro.irpf.aplicacao.dominio.PaginaPdf;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Pdf {
    List<PaginaPdf> extraiPaginasPdf(MultipartFile arquivoFile);
}
