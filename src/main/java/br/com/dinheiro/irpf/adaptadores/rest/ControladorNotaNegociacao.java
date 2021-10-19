package br.com.dinheiro.irpf.adaptadores.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import br.com.dinheiro.irpf.aplicacao.api.ServicoNotaNegociacao;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notas")
public class ControladorNotaNegociacao {

    private final ServicoNotaNegociacao servico;

    public ControladorNotaNegociacao(ServicoNotaNegociacao servico) {
        this.servico = servico;
    }

    @GetMapping(path = "/tes", produces = {APPLICATION_JSON_VALUE})
    public String getInstallmentOption(@RequestParam("arquivo") String arquivo) {
        System.out.println(arquivo);
        servico.notasDeNegociacao(arquivo);

        return "vamos ver";
    }

}
