package com.qualidade.qualidade;

public class TipoID {
    private int valor;

    public TipoID() {
        this.valor = 0;
    }

    public TipoID(int valor) {
        this.valor = valor;
    }

    public void set(int valor) {
        this.valor = valor;
    }

    public int get() {
        return this.valor;
    }

    public boolean igual(TipoID outro) {
        return this.valor == outro.valor;
    }

    public TipoID copiar() {
        return new TipoID(this.valor);
    }
}
