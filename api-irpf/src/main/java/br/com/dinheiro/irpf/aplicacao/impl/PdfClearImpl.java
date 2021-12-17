package br.com.dinheiro.irpf.aplicacao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.dinheiro.irpf.aplicacao.api.ServicoPdfClear;
import br.com.dinheiro.irpf.aplicacao.dominio.PaginaPdf;
import br.com.dinheiro.irpf.aplicacao.dominio.Negociacao;
import br.com.dinheiro.irpf.aplicacao.impl.dto.NegociacaoDTO;
import br.com.dinheiro.irpf.aplicacao.impl.dto.OperacaoDto;
import br.com.dinheiro.irpf.aplicacao.repositorio.Pdf;
import static br.com.dinheiro.irpf.util.Util.getLinhaSeparada;

import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

public class PdfClearImpl implements ServicoPdfClear {

	private static final String REGEX_SE_POSSUI_NUEMRO = ".*\\d.*";
	private static final String REGEX_SE_SO_POSSUI_NUEMRO = "^\\d";

	private final static String BOVESPA = "1-BOVESPA";
	private final static String DATA_PREGAO = "Data pregão";
	private final static String NUMERO_NOTA = "Nr. nota";
	private final static String CLIENTE = "Cliente";
	private final static String TOTAL_CBLC = "Total CBLC";
	private final static String VALOR_LIQUIDO_DAS_OPERACOES = "Valor líquido das operações";
	private final static String VALOR_LIQUIDO_PARA = "Líquido para";
	private final static String CPF_CLIENTE = "Conta corrente Acionista Administrador";

	private final static String TAXA_REGISTRO = "Taxa de Registro";
	private final static String TAXA_LIQUIDA = "Taxa de liquidação";
	private final static String TAXA_TEMPO_OPERACAO = "Taxa de termo/opções";
	private final static String TAXA_ANA = "Taxa A.N.A.";
	private final static String EMOLUMENTOS = "Emolumentos";
	private final static String TAXA_OPERACIONAL = "Taxa Operacional";
	private final static String TAXA_EXECUCAO = "Execução";
	private final static String TAXA_CUSTODIA = "Taxa de Custódia";
	private final static String TAXA_IMPOSTOS = "Impostos";
	private final static String TAXA_IRRF = "I.R.R.F.";
	private final static String TAXA_OUTRAS = "Outros";

	private Pdf pdf;
	private Conversor conversor;

	public PdfClearImpl(Pdf pdf) {
		this.pdf = pdf;
		this.conversor = new Conversor();
	}

	@Override
	public List<Negociacao> notaNegociacao(MultipartFile arquivo) {
		List<PaginaPdf> paginas = pdf.extraiPaginasPdf(arquivo);
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
				dtoNegociacao.setTaxaLiquidacao(getTaxa(linha, TAXA_LIQUIDA));

			if(linha.contains(TAXA_REGISTRO))
				dtoNegociacao.setTaxaRegistro(getTaxa(linha, TAXA_REGISTRO));

			if(linha.contains(TAXA_TEMPO_OPERACAO))
				dtoNegociacao.setTaxaTempoOperacao(getTaxa(linha, TAXA_TEMPO_OPERACAO));

			if(linha.contains(TAXA_ANA))
				dtoNegociacao.setTaxaANA(getTaxa(linha, TAXA_ANA));

			if(linha.contains(EMOLUMENTOS))
				dtoNegociacao.setEmolumentos(getTaxa(linha, EMOLUMENTOS));

			if(linha.contains(TAXA_OPERACIONAL))
				dtoNegociacao.setTaxaOperacional(getTaxa(linha, TAXA_OPERACIONAL));

			if(linha.contains(TAXA_EXECUCAO))
				dtoNegociacao.setTaxaExecucao(getTaxa(linha, TAXA_EXECUCAO));

			if(linha.contains(TAXA_CUSTODIA))
				dtoNegociacao.setTaxaCustodia(getTaxa(linha, TAXA_CUSTODIA));

			if(linha.contains(TAXA_IMPOSTOS))
				dtoNegociacao.setImpostos(getTaxa(linha, TAXA_IMPOSTOS));

			if(linha.contains(TAXA_IRRF))
				dtoNegociacao.setIrrf(getTaxa(linha, TAXA_IRRF));

			if(linha.contains(TAXA_OUTRAS))
				dtoNegociacao.setOutrasTaxas(getTaxa(linha, TAXA_OUTRAS));
		}

		dtoNegociacao.setOperacao(dtoDeOperacoes);
		return dtoNegociacao;
	}
	
	private String getTaxa(String linhaDaTaxa, String taxa) {
		List<String> linhaSeparada = getLinhaSeparada(linhaDaTaxa, taxa);
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
