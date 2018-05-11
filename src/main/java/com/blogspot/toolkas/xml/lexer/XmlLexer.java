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

        if (value == null || value.trim().length() == 0) {
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
                    state = TokenizeState.START;
                    position -= 1;
                } else if (ch == '/') {
                    state = TokenizeState.SLASH;
                    position -= 1;
                } else if (ch == '>') {
                    state = TokenizeState.END;
                    position -= 1;
                } else if (Character.isWhitespace(ch)) {
                    return null;
                } else {
                    throw new TokenizeException(
                            "unexpected character '" + ch +
                                    "' in state " + state
                    );
                }
                break;
            case START:
                return createToken(Token.START, false);
            case END:
                return createToken(Token.END, false);
            case SLASH:
                return createToken(Token.SLASH, false);
        }
        return null;
    }

    private int get() {
        position += 1;
        return position < expression.length() ?
                expression.charAt(position) : -1;
    }

    private Lexem createToken(Token token, boolean back) {
        Lexem lexem = new Lexem(token, value);
        value = "";

        state = TokenizeState.DEFAULT;
        if (back) {
            position -= 1;
        }
        return lexem;
    }

    private enum TokenizeState {
        DEFAULT, START, END, SLASH;
    }
}
