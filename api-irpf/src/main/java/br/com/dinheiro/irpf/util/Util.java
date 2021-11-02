package br.com.dinheiro.irpf.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class Util {

    public static List<String> getLinhaSeparada(String linha, String regexSeparador) {
        List<String> linhaSeparada = !Strings.isNullOrEmpty(linha) ? Arrays.asList(linha.split(regexSeparador)) : null;
        return linhaSeparada;
    }
}
