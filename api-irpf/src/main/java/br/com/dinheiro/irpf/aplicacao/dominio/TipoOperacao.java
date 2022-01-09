package br.com.dinheiro.irpf.aplicacao.dominio;

public enum TipoOperacao {

        COMPRA('C'), VENDA('V');

    private char operacao;

    TipoOperacao(char operacao) {
        this.operacao = operacao;
    }

    public static TipoOperacao porAbreviacao(String abreviacao) {
        char abreviacaoConvertida = abreviacao.charAt(0);
        for(TipoOperacao valor : TipoOperacao.values()) {
            if(abreviacaoConvertida == valor.operacao)
                return valor;
        }
        throw new IllegalArgumentException("Tipo de operação é invalido");
    }

    public char getOperacao() {
        return operacao;
    }
}
