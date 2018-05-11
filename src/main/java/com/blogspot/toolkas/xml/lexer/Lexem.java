package com.blogspot.toolkas.xml.lexer;

public class Lexem {
    private final Token token;
    private final String value;

    public Lexem(Token token, String value) {
        this.token = token;
        this.value = value;
    }

    public Token getToken() {
        return token;
    }

    public String getValue() {
        return value;
    }
}
