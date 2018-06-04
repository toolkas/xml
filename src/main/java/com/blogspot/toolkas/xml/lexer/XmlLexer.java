package com.blogspot.toolkas.xml.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class XmlLexer {
    private TokenizeState state = TokenizeState.DEFAULT;
    private String expression;
    private String value = "";
    private int position = -1;

    public XmlLexer(String expression) {
        Objects.requireNonNull(expression, "expression can't be null");
        this.expression = expression;
    }

    public List<Lexem> tokenize() throws TokenizeException {
        List<Lexem> lexems = new ArrayList<>();

        int ch;
        while ((ch = get()) != -1) {
            Lexem lexem = nextLexem(ch);
            if (lexem != null) {
                lexems.add(lexem);
            }
        }

        if (state != TokenizeState.DEFAULT) {
            Lexem lexem = nextLexem(ch);
            if (lexem != null) {
                lexems.add(lexem);
            }
        }

        if (value != null && value.trim().length() > 0) {
            throw new TokenizeException("can't parse '" + value + "'");
        }

        lexems.add(new Lexem(Token.EOF, null));

        return lexems;
    }

    private Lexem nextLexem(int b) throws TokenizeException {
        char ch = (char) b;

        switch (state) {
            case DEFAULT:
                if (ch == '<') {
                    state = TokenizeState.ELEMENT;
                    return new Lexem(Token.LT, "<");
                } else if (Character.isWhitespace(ch)) {
                    return null;
                } else {
                    state = TokenizeState.TEXT;
                    value += ch;
                }
                break;
            case TEXT:
                if (ch == '<') {
                    state = TokenizeState.DEFAULT;
                    position -= 1;

                    try {
                        return new Lexem(Token.TEXT, value);
                    } finally {
                        value = "";
                    }
                }
                value += ch;
                break;
            case ELEMENT:
                if (ch == '"') {
                    state = TokenizeState.STRING;
                } else if (ch == '=') {
                    return new Lexem(Token.EQ, "=");
                } else if (ch == '/') {
                    return new Lexem(Token.SLASH, "/");
                } else if (ch == '>') {
                    state = TokenizeState.DEFAULT;
                    return new Lexem(Token.GT, ">");
                } else if (Character.isWhitespace(ch)) {
                    return null;
                } else if (Character.isAlphabetic(ch)) {
                    state = TokenizeState.NAME;
                    value += ch;
                }
                break;
            case STRING:
                if (ch == '"') {
                    state = TokenizeState.ELEMENT;
                    try {
                        return new Lexem(Token.STRING, value);
                    } finally {
                        value = "";
                    }
                }
                value += ch;
                break;
            case NAME:
                if (Character.isAlphabetic(ch) || Character.isDigit(ch) || ch == '_' || ch == '-' || ch == ':') {
                    value += ch;
                } else {
                    state = TokenizeState.ELEMENT;
                    position -= 1;
                    try {
                        return new Lexem(Token.NAME, value);
                    } finally {
                        value = "";
                    }
                }

        }
        return null;
    }

    private int get() {
        position += 1;
        return position < expression.length() ?
                expression.charAt(position) : -1;
    }

    private enum TokenizeState {
        DEFAULT, ELEMENT, NAME, STRING, TEXT;
    }
}
