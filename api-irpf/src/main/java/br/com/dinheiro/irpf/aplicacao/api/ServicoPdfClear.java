package br.com.dinheiro.irpf.aplicacao.api;


import br.com.dinheiro.irpf.aplicacao.dominio.Negociacao;
import br.com.dinheiro.irpf.aplicacao.impl.NegociacaoDTO;

import java.util.List;

public interface ServicoPdfClear {
    List<NegociacaoDTO> notaNegociacaoDto(String nomeArquivo);
}
