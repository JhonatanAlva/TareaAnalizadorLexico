package org.example;

public class ElementoLexico {
    private final String lexema;
    private final String token;

    public ElementoLexico(String lexema, String token){
        this.lexema = lexema;
        this.token = token;
    }

    public String obtenerLexema() {
        return lexema;
    }

    public String obtenerToken() {
        return token;
    }
}

