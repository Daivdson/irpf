package br.com.dinheiro.irpf.aplicacao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.dinheiro.irpf.aplicacao.api.ServicoPdfClear;
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
	private final static String EMONUMENTOS = "Emolumentos";
	private final static String TAXA_IRRF = "I.R.R.F.";
	private static final String REGEX_SE_POSSUI_NUEMRO = ".*\\d.*";
	private static final String REGEX_SE_SO_POSSUI_NUEMRO = "^\\d";

	private Pdf pdf;

	public PdfClearImpl(Pdf pdf) {
		this.pdf = pdf;
	}
	
	@Override
	public List<Negociacao> notaNegociacao(String nomeArquivo) {
		List<PaginaPdf> paginas = pdf.extraiPaginasPdf(nomeArquivo);
		List<NegociacaoDTO> negociacoesDTO = new ArrayList<>();

		for (PaginaPdf pagina: paginas) {
			negociacoesDTO.add(getNegociacao(pagina.getLinhas()));
		}

		return converterDtoNegociacoes(negociacoesDTO);
	}

	private List<Negociacao> converterDtoNegociacoes(List<NegociacaoDTO> negociacoesDTO) {
		return negociacoesDTO.stream()
				.map(this::converterDtoNegociacao)
				.collect(Collectors.toList());
	}

	private Negociacao converterDtoNegociacao(NegociacaoDTO negociacaoDTO) {
		return new Negociacao(negociacaoDTO.getOperacao(),
				negociacaoDTO.getNomeCliente(),
				negociacaoDTO.getCpf(),
				negociacaoDTO.getIdCliente(),
				negociacaoDTO.getDataNegociacao(),
				negociacaoDTO.getTaxaLiquidacao(),
				negociacaoDTO.getEmonumentos(),
				negociacaoDTO.getIrrf(),
				negociacaoDTO.getNumeroNota());
	}

	private NegociacaoDTO getNegociacao(List<String> linhas) {
		NegociacaoDTO dtoNegociacao = new NegociacaoDTO();
		List<OperacaoDto> dtoDeOperaces = new ArrayList<>();

		boolean isClientePassou = false;
		boolean isClienteJaPreenchido = false;
		boolean isNumeroNotaPassou = false;
		boolean isDataPregaoPassou = false;
		boolean isCpdfClientePassou = false;

		for (String linha: linhas) {
			// Operacoes
			if (linha.contains(BOVESPA)) {
				dtoDeOperaces.add(extraiOperacao(linha));
			}
			// dados cliente
			if(linha.contains(CLIENTE) && !isClientePassou && !isClienteJaPreenchido){
				isClientePassou = true;
				continue;
			}else if(isClientePassou && !isClienteJaPreenchido) {
				isClienteJaPreenchido = true;
				dtoNegociacao.setNomeCliente(
						extraiNomeDoCliente(linha));
				dtoNegociacao.setIdCliente(extraiIdDoCliente(linha));
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

			if(linha.contains(EMONUMENTOS))
				dtoNegociacao.setEmonumentos(getEmonumentos(linha));

			if(linha.contains(TAXA_IRRF))
				dtoNegociacao.setIrrf(getTaxaIrrf(linha));
		}

		dtoNegociacao.setOperacao(dtoDeOperaces);
		return dtoNegociacao;
	}

	private String getTaxaLiquida(String linhaDaTaxaLiquida) {
		List<String> linhaSeparada = getLinhaSeparada(linhaDaTaxaLiquida, TAXA_LIQUIDA);
		return linhaSeparada != null ? linhaSeparada.get(0) : null;
	}

	private String getEmonumentos(String linhaDeEmonumentos) {
		List<String> linhaSeparada = getLinhaSeparada(linhaDeEmonumentos, EMONUMENTOS);
		return linhaSeparada != null ? linhaSeparada.get(0) : null;
	}

	private String getTaxaIrrf(String linhaIrrf) {
		List<String> linhaSeparada = getLinhaSeparada(linhaIrrf, TAXA_IRRF);
		return linhaSeparada != null ? linhaSeparada.get(0) : null;
	}

	private String extraiNomeDoCliente(String linhaComDadosDoCliente) {
		List<String> linhaSeparada = getLinhaSeparada(linhaComDadosDoCliente, " ");
		return linhaSeparada != null ?
				linhaSeparada.stream()
						.filter(v -> !v.matches(REGEX_SE_POSSUI_NUEMRO)) //Filtra tudo que é diferente de numero que é = ao nome cliente
						.collect(Collectors.joining(" ")):
				null;
	}

	private String extraiIdDoCliente(String linhaComIdDoCliente) {
		List<String> linhaSeparada = getLinhaSeparada(linhaComIdDoCliente, " ");
		int posicaoQueEstaOIdCliente = 0;
		return linhaSeparada != null ?
				linhaSeparada.get(posicaoQueEstaOIdCliente) :
				null;
	}

	private OperacaoDto extraiOperacao(String linhaComDadosDaOperacao) {
		// Relacao de posição do array para os dados
		int posicaoTipoNegociacao = 0;
		int posicaoTipoOperacao = 1;
		int posicaoTipoMercado = 2;
		int posicaoAtivo = 3;

		List<String> linhaOperacaoSeparada = getLinhaSeparada(linhaComDadosDaOperacao, " ");

		OperacaoDto operacaoDto = OperacaoDto.builder()
				.tipoOperacao(linhaOperacaoSeparada.get(posicaoTipoOperacao))
				.tipoNegociacao(linhaOperacaoSeparada.get(posicaoTipoNegociacao))
				.tipoMercado(linhaOperacaoSeparada.get(posicaoTipoMercado))
				.ativo(linhaOperacaoSeparada.get(posicaoAtivo))
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
