package br.com.dinheiro.irpf.aplicacao.dominio;

public enum TipoOperacao {

        COMPRA('C'), VENDA('V');

    private char operacao;

    TipoOperacao(char operacao) {
        this.operacao = operacao;
    }

    public char getOperacao() {
        return operacao;
    }
}
