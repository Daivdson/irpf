package br.com.dinheiro.irpf.adaptadores.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ControladorBemVindo {

    @GetMapping(path = {"/", ""}, produces = {APPLICATION_JSON_VALUE})
    public String bemVindo() {
        return "Achou!!!";
    }
}
