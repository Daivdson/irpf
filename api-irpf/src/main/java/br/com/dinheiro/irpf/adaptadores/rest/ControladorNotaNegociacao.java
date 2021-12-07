package br.com.dinheiro.irpf.adaptadores.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

import br.com.dinheiro.irpf.aplicacao.api.ServicoPdfClear;
import br.com.dinheiro.irpf.aplicacao.dominio.Negociacao;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notas")
public class ControladorNotaNegociacao {

    private final ServicoPdfClear servico;

    public ControladorNotaNegociacao(ServicoPdfClear servico) {
        this.servico = servico;
    }

    @PostMapping(path = "/importacao", produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity importacao(@RequestParam MultipartFile arquivo) {

        if(!arquivo.getContentType().equals(APPLICATION_PDF_VALUE))
           return new ResponseEntity("Tipo de arquivo invalido para requisição", HttpStatus.UNSUPPORTED_MEDIA_TYPE);

        List<Negociacao> dadosDoPdf = servico.notaNegociacao(arquivo);

        JSONObject json = new JSONObject();
        json.put("nome", arquivo.getName());
        json.put("tamanhoDoArquivo", arquivo.getSize());
        json.put("tipoDoArquivo", arquivo.getContentType());
        json.put("nomeOriginal", arquivo.getOriginalFilename());
        json.put("dadosDoPdfExtraido", dadosDoPdf);

        return new ResponseEntity(json, HttpStatus.OK);
    }

}
