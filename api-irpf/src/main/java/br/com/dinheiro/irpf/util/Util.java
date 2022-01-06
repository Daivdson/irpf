package br.com.dinheiro.irpf.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.google.common.base.Strings;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Util {

    public static List<String> getLinhaSeparada(String linha, String regexSeparador) {
        List<String> linhaSeparada = !Strings.isNullOrEmpty(linha) ? Arrays.asList(linha.split(regexSeparador)) : null;
        return linhaSeparada;
    }

    public static Date formatarData(String data){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = formato.parse(data);
        } catch (ParseException e) {
            log.error("Erro ao tentar converter data ", e.getMessage(), e);
        }
        return date;
    }

    public static BigDecimal somaBigDecimal(BigDecimal ... valor) {
        return Arrays.stream(valor).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal somaBigDecimal(List<BigDecimal> valor) {
        return valor.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
