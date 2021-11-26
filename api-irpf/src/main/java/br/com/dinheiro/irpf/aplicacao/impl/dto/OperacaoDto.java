package br.com.dinheiro.irpf.aplicacao.impl.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
@Builder
public class OperacaoDto {
    private String tipoNegociacao;
    private String tipoOperacao;
    private String tipoMercado;
    private String ativo;
    private String quantidade;
    private String preco;
    private String valorOperacao;
    private String tipoAcao;

}
