package br.com.dinheiro.irpf.aplicacao.api;


import br.com.dinheiro.irpf.aplicacao.dominio.Negociacao;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ServicoPdfClear {
    List<Negociacao> notaNegociacao(String nomeArquivo);

    List<Negociacao> notaNegociacao(MultipartFile arquivo);
}
