package br.com.dinheiro.irpf.aplicacao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.dinheiro.irpf.aplicacao.api.ServicoPdfClear;
import br.com.dinheiro.irpf.aplicacao.dominio.PaginaPdf;
import br.com.dinheiro.irpf.aplicacao.dominio.Negociacao;
import br.com.dinheiro.irpf.aplicacao.repositorio.Pdf;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

public class PdfClearImpl implements ServicoPdfClear {

	private final static String BOVESPA = "1-BOVESPA";
	private final static String DATA_PREGAO = "Data pregão";//quebra
	private final static String NUMERO_NOTA = "Nr. nota";//quebra
	private final static String CLIENTE = "Cliente";// quebra
	private final static String TOTAL_CBLC = "Total CBLC";
	private final static String VALOR_LIQUIDO_DAS_OPERACOES = "Valor líquido das operações";
	private final static String TAXA_LIQUIDA = "Taxa de liquidação";
	private final static String VALOR_LIQUIDO_PARA = "Líquido para";
	private final static String CPF_CLIENTE = "Conta corrente Acionista Administrador"; // quebra
	private final static String EMONUMENTOS = "Emolumentos";
	private final static String TAXA_IRRF = "I.R.R.F.";

	private Pdf pdf;

	public PdfClearImpl(Pdf pdf) {
		this.pdf = pdf;
	}
	
	@Override
	public List<Negociacao> notaNegociacao(String nomeArquivo) {
		List<Negociacao> negociacao = new ArrayList<>();
		List<PaginaPdf> paginas = pdf.extraiPaginasPdf(nomeArquivo);
		List<String> negociacoesPagina01 = new ArrayList<>();

		List<NegociacaoDTO> negociacoesDTO = new ArrayList<>();
		boolean isClientePassou = false;
		boolean isClienteJaPreenchido = false;
		boolean isNumeroNotaPassou = false;
		boolean isDataPregaoPassou = false;
		boolean isCpdfClientePassou = false;


		for (PaginaPdf pagina: paginas) {
			NegociacaoDTO negociacaoDTO = new NegociacaoDTO();
			List<OperacaoDto> operacesDto = new ArrayList<>();
			for (String linha: pagina.getLinhas()) {
				// Operacoes
				if (linha.contains(BOVESPA)) {
					operacesDto.add(
							extraindoOperacao(linha));
				}
				// dados cliente
				if(linha.contains(CLIENTE) && !isClientePassou && !isClienteJaPreenchido){
					isClientePassou = true;
					continue;
				}else if(isClientePassou && !isClienteJaPreenchido) {
					isClienteJaPreenchido = true;
					negociacaoDTO.setNomeCliente(
							extraindoNomeDoCliente(linha));
					negociacaoDTO.setIdCliente(extraindoIdDoCliente(linha));
				}

				if(linha.equals(NUMERO_NOTA) && !isNumeroNotaPassou){
					isNumeroNotaPassou = true;
					continue;
				}else if(isNumeroNotaPassou) {
					isNumeroNotaPassou = false;
					negociacaoDTO.setNumeroNota(linha);
				}

				if(linha.equals(DATA_PREGAO) && !isDataPregaoPassou){
					isDataPregaoPassou = true;
					continue;
				}else if(isDataPregaoPassou) {
					isDataPregaoPassou = false;
					negociacaoDTO.setDataNegociacao(linha);
				}

				if(linha.equals(CPF_CLIENTE) && !isCpdfClientePassou){
					isCpdfClientePassou = true;
					continue;
				}else if(isCpdfClientePassou) {
					isCpdfClientePassou = false;
					negociacaoDTO.setCpf(linha);
				}

				if(linha.contains(TAXA_LIQUIDA)) {
					negociacaoDTO.setTaxaLiquidacao(linha);
				}

				if(linha.contains(EMONUMENTOS)) {
					negociacaoDTO.setEmonumentos(linha);
				}

				if(linha.contains(TAXA_IRRF)) {
					negociacaoDTO.setIrrf(linha);
				}


			}
			negociacaoDTO.setOperacao(operacesDto);
			negociacoesDTO.add(negociacaoDTO);
		}
		String json = null;
		try {
			json = new ObjectMapper().writeValueAsString(negociacoesDTO);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return negociacao;
	}

	private String extraindoNomeDoCliente(String linha) {
		// extraindo nome cliente
		List<String> linhaSeparada = !Strings.isNullOrEmpty(linha) ? Arrays.asList(linha.split(" ")) : null;
		return linhaSeparada != null ?
				linhaSeparada.stream().filter(v -> !v.matches(".*\\d.*")).collect(Collectors.joining(" "))
				: null;
	}

	private String extraindoIdDoCliente(String linha) {
		// extraindo nome cliente
		String linhaSeparada[] = !Strings.isNullOrEmpty(linha) ? linha.split(" ") : null;
		return linhaSeparada != null ? linhaSeparada[0] : null;
	}

	private OperacaoDto extraindoOperacao(String linha) {
		List<String> operacao = Arrays.asList(linha.split(" "));

		// Relacao de posição do array para os dados
		int posicaoTipoNegociacao = 0;
		int posicaoTipoOperacao = 1;
		int posicaoTipoMercado = 2;
		int posicaoAtivo = 3;

		String quantidade = null;
		String preco = null;
		String valorOperacao = null;

		boolean primeiroValorPassou = false;
		boolean segundoValorPassou = false;
		boolean terceiroValorPassou = false;

		for (String dado: operacao) {
			// is numero
			//if(dado.matches("^\\d")) { // verifica se só tem numero
			if(dado.matches(".*\\d.*") && !dado.equals(BOVESPA)) {
				if(!primeiroValorPassou) {
					quantidade = dado;
					primeiroValorPassou = true;
					continue;
				}
				if(primeiroValorPassou && !segundoValorPassou) {
					preco = dado;
					segundoValorPassou = true;
					continue;
				}
				if(segundoValorPassou && !terceiroValorPassou) {
					valorOperacao = dado;
					terceiroValorPassou = true;
					continue;
				}
			}
		}
		OperacaoDto operacaoDto = OperacaoDto.builder()
				.tipoOperacao(operacao.get(posicaoTipoOperacao))
				.tipoNegociacao(operacao.get(posicaoTipoNegociacao))
				.tipoMercado(operacao.get(posicaoTipoMercado))
				.ativo(operacao.get(posicaoAtivo))
				.quantidade(quantidade)
				.preco(preco)
				.valorOperacao(valorOperacao)
				.build();
		return operacaoDto;
	}

}
