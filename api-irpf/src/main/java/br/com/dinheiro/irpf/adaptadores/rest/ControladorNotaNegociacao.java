package br.com.dinheiro.irpf.adaptadores.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import br.com.dinheiro.irpf.aplicacao.api.ServicoPdfClear;
import br.com.dinheiro.irpf.aplicacao.impl.ConversorDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notas")
public class ControladorNotaNegociacao {

    private final ServicoPdfClear servico;

    public ControladorNotaNegociacao(ServicoPdfClear servico) {
        this.servico = servico;
    }

    @GetMapping(path = "/tes", produces = {APPLICATION_JSON_VALUE})
    public List importarNotaNegociacao(@RequestParam("arquivo") String arquivo) {
        System.out.println(arquivo);
        return servico.notaNegociacao(arquivo).stream()
                .map(ConversorDto::retornaNegociacao)
                .collect(Collectors.toList());
    }

}
