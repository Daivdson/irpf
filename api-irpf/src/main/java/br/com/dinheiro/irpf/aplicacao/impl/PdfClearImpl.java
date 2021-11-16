package br.com.dinheiro.irpf.aplicacao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.dinheiro.irpf.aplicacao.api.ServicoPdfClear;
import br.com.dinheiro.irpf.aplicacao.dominio.Conversor;
import br.com.dinheiro.irpf.aplicacao.dominio.PaginaPdf;
import br.com.dinheiro.irpf.aplicacao.dominio.Negociacao;
import br.com.dinheiro.irpf.aplicacao.repositorio.Pdf;
import static br.com.dinheiro.irpf.util.Util.getLinhaSeparada;

import org.springframework.util.CollectionUtils;

public class PdfClearImpl implements ServicoPdfClear {

	private final static String BOVESPA = "1-BOVESPA";
	private final static String DATA_PREGAO = "Data pregão";
	private final static String NUMERO_NOTA = "Nr. nota";
	private final static String CLIENTE = "Cliente";
	private final static String TOTAL_CBLC = "Total CBLC";
	private final static String VALOR_LIQUIDO_DAS_OPERACOES = "Valor líquido das operações";
	private final static String TAXA_LIQUIDA = "Taxa de liquidação";
	private final static String VALOR_LIQUIDO_PARA = "Líquido para";
	private final static String CPF_CLIENTE = "Conta corrente Acionista Administrador";
	private final static String EMOLUMENTOS = "Emolumentos";
	private final static String TAXA_IRRF = "I.R.R.F.";
	private static final String REGEX_SE_POSSUI_NUEMRO = ".*\\d.*";
	private static final String REGEX_SE_SO_POSSUI_NUEMRO = "^\\d";

	private Pdf pdf;
	private Conversor conversor;

	public PdfClearImpl(Pdf pdf) {
		this.pdf = pdf;
		conversor = new Conversor();
	}
	
	@Override
	public List<Negociacao> notaNegociacao(String nomeArquivo) {
		List<PaginaPdf> paginas = pdf.extraiPaginasPdf(nomeArquivo);
		List<NegociacaoDTO> negociacoesDTO = new ArrayList<>();

		paginas.stream().forEach(pagina -> negociacoesDTO.
				add(getNegociacao(pagina.getLinhas())));

		return conversor.converterDtoNegociacoes(negociacoesDTO);
	}


	private NegociacaoDTO getNegociacao(List<String> linhas) {
		NegociacaoDTO dtoNegociacao = new NegociacaoDTO();
		List<OperacaoDto> dtoDeOperacoes = new ArrayList<>();

		boolean isClientePassou = false;
		boolean isClienteJaPreenchido = false;
		boolean isNumeroNotaPassou = false;
		boolean isDataPregaoPassou = false;
		boolean isCpdfClientePassou = false;

		for (String linha: linhas) {
			// Operacoes
			if (linha.contains(BOVESPA)) {
				dtoDeOperacoes.add(getOperacao(linha));
			}
			// dados cliente
			if(linha.contains(CLIENTE) && !isClientePassou && !isClienteJaPreenchido){
				isClientePassou = true;
				continue;
			}else if(isClientePassou && !isClienteJaPreenchido) {
				isClienteJaPreenchido = true;
				dtoNegociacao.setNomeCliente(
						getNomeDoCliente(linha));
				dtoNegociacao.setIdCliente(getIdDoCliente(linha));
			}

			if(linha.equals(NUMERO_NOTA) && !isNumeroNotaPassou){
				isNumeroNotaPassou = true;
				continue;
			}else if(isNumeroNotaPassou) {
				isNumeroNotaPassou = false;
				dtoNegociacao.setNumeroNota(linha);
			}

			if(linha.equals(DATA_PREGAO) && !isDataPregaoPassou){
				isDataPregaoPassou = true;
				continue;
			}else if(isDataPregaoPassou) {
				isDataPregaoPassou = false;
				dtoNegociacao.setDataNegociacao(linha);
			}

			if(linha.equals(CPF_CLIENTE) && !isCpdfClientePassou){
				isCpdfClientePassou = true;
				continue;
			}else if(isCpdfClientePassou) {
				isCpdfClientePassou = false;
				dtoNegociacao.setCpf(linha);
			}

			if(linha.contains(TAXA_LIQUIDA))
				dtoNegociacao.setTaxaLiquidacao(getTaxaLiquida(linha));

			if(linha.contains(EMOLUMENTOS))
				dtoNegociacao.setEmolumentos(getEmolumentos(linha));

			if(linha.contains(TAXA_IRRF))
				dtoNegociacao.setIrrf(getTaxaIrrf(linha));
		}

		dtoNegociacao.setOperacao(dtoDeOperacoes);
		return dtoNegociacao;
	}

	private String getTaxaLiquida(String linhaDaTaxaLiquida) {
		List<String> linhaSeparada = getLinhaSeparada(linhaDaTaxaLiquida, TAXA_LIQUIDA);
		return linhaSeparada != null ? linhaSeparada.get(0) : null;
	}

	private String getEmolumentos(String linhaDeEmolumentos) {
		List<String> linhaSeparada = getLinhaSeparada(linhaDeEmolumentos, EMOLUMENTOS);
		return linhaSeparada != null ? linhaSeparada.get(0) : null;
	}

	private String getTaxaIrrf(String linhaIrrf) {
		List<String> linhaSeparada = getLinhaSeparada(linhaIrrf, TAXA_IRRF);
		return linhaSeparada != null ? linhaSeparada.get(0) : null;
	}

	private String getNomeDoCliente(String linhaComDadosDoCliente) {
		List<String> linhaSeparada = getLinhaSeparada(linhaComDadosDoCliente, " ");
		return linhaSeparada != null ?
				linhaSeparada.stream()
						.filter(v -> !v.matches(REGEX_SE_POSSUI_NUEMRO)) //Filtra tudo que é diferente de numero que é = ao nome cliente
						.collect(Collectors.joining(" ")):
				null;
	}

	private String getIdDoCliente(String linhaComIdDoCliente) {
		List<String> linhaSeparada = getLinhaSeparada(linhaComIdDoCliente, " ");
		int posicaoQueEstaOIdCliente = 0;
		return linhaSeparada != null ?
				linhaSeparada.get(posicaoQueEstaOIdCliente) :
				null;
	}

	private OperacaoDto getOperacao(String linhaComDadosDaOperacao) {
		// Relacao de posição do array para os dados
		int posicaoTipoNegociacao = 0;
		int posicaoTipoOperacao = 1;
		int posicaoTipoMercado = 2;
		int posicaoAtivo = 3;
		int posicaoTipoAcao = 13;

		List<String> linhaOperacaoSeparada = getLinhaSeparada(linhaComDadosDaOperacao, " ");

		OperacaoDto operacaoDto = OperacaoDto.builder()
				.tipoOperacao(linhaOperacaoSeparada.get(posicaoTipoOperacao))
				.tipoNegociacao(linhaOperacaoSeparada.get(posicaoTipoNegociacao))
				.tipoMercado(linhaOperacaoSeparada.get(posicaoTipoMercado))
				.ativo(linhaOperacaoSeparada.get(posicaoAtivo))
				.tipoAcao(linhaOperacaoSeparada.get(posicaoTipoAcao))
				.build();

		setValoresQuantidadePrecoValorOperacao(operacaoDto, linhaOperacaoSeparada.stream()
				.filter(v -> v.matches(REGEX_SE_POSSUI_NUEMRO) && !v.equals(BOVESPA))
				.collect(Collectors.toList()));

		return operacaoDto;
	}

	private void setValoresQuantidadePrecoValorOperacao (OperacaoDto operacaoDto, List<String> valoresQuantidadePrecoValorOperacao) {
		int quantidadeDePosicoesComValoresDaOperacao = 3;
		int posicaoQuantidade = 0;
		int posicaoPreco = 1;
		int posicaoValorOperacao = 2;
		if (!CollectionUtils.isEmpty(valoresQuantidadePrecoValorOperacao) && valoresQuantidadePrecoValorOperacao.size() == quantidadeDePosicoesComValoresDaOperacao) {
			operacaoDto.setQuantidade(valoresQuantidadePrecoValorOperacao.get(posicaoQuantidade));
			operacaoDto.setPreco(valoresQuantidadePrecoValorOperacao.get(posicaoPreco));
			operacaoDto.setValorOperacao(valoresQuantidadePrecoValorOperacao.get(posicaoValorOperacao));
		}
	}

}
