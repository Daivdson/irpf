package br.com.dinheiro.irpf.adaptadores.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import br.com.dinheiro.irpf.aplicacao.api.ServicoPdfClear;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notas")
public class ControladorNotaNegociacao {

    private final ServicoPdfClear servico;

    public ControladorNotaNegociacao(ServicoPdfClear servico) {
        this.servico = servico;
    }

    @GetMapping(path = "/tes", produces = {APPLICATION_JSON_VALUE})
    public String importarNotaNegociacao(@RequestParam("arquivo") String arquivo) {
        System.out.println(arquivo);
        servico.notaNegociacao(arquivo);

        return "Nota de negociacao";
    }

}
