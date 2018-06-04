package com.blogspot.toolkas.xml.parser;

import com.blogspot.toolkas.xml.lexer.Lexem;
import com.blogspot.toolkas.xml.lexer.Token;
import com.blogspot.toolkas.xml.model.Attribute;
import com.blogspot.toolkas.xml.model.Document;
import com.blogspot.toolkas.xml.model.Element;

import java.util.List;
import java.util.Objects;

public class XmlParser {
    private int position = 0;

    public Document parse(List<Lexem> lexems) throws XmlParseException {
        Element element = element(lexems);
        return new Document(element);
    }

    private Element element(List<Lexem> lexems) throws XmlParseException {
        consume(lexems, Token.LT);

        Lexem name = consume(lexems, Token.NAME);

        Element element = new Element(name.getValue());
        while (check(lexems, Token.NAME)) {
            Lexem lexem = prev(lexems);
            consume(lexems, Token.EQ);

            Lexem value = consume(lexems, Token.STRING);

            Attribute attribute = new Attribute(lexem.getValue(), value.getValue());
            element.add(attribute);
        }

        if (check(lexems, Token.SLASH)) {
            consume(lexems, Token.GT);
        } else {
            consume(lexems, Token.GT);
            if (check(lexems, Token.TEXT)) {
                Lexem text = prev(lexems);
                element.setText(text.getValue());
            } else {
                while (check(lexems, Token.LT)) {
                    if(check(lexems, Token.SLASH)) {
                        back();
                        back();

                        break;
                    }
                    back();

                    Element el = element(lexems);
                    element.addChild(el);
                }
            }

            consume(lexems, Token.LT);
            consume(lexems, Token.SLASH);
            Lexem name2 = consume(lexems, Token.NAME);
            consume(lexems, Token.GT);

            if (!Objects.equals(name.getValue(), name2.getValue())) {
                throw new XmlParseException("names are not equals");
            }
        }

        return element;
    }

    private void back() {
        position -= 1;
    }

    protected Lexem get(final List<Lexem> lexems, int offset) {
        if (position + offset >= lexems.size()) {
            return new Lexem(Token.EOF, null);
        }

        return lexems.get(position + offset);
    }

    protected boolean check(final List<Lexem> lexems, Token type) {
        Lexem token = get(lexems, 0);
        if (token.getToken() != type) {
            return false;
        }
        position++;
        return true;
    }

    protected Lexem consume(final List<Lexem> lexems, Token token) throws XmlParseException {
        Lexem lexem = get(lexems, 0);
        if (lexem.getToken() != token) {
            throw new XmlParseException("expected {" + token + "}, but consumed {" + lexem.getToken() + "}");
        }
        return lexems.get(position++);
    }

    private Lexem prev(List<Lexem> lexems) {
        return lexems.get(position - 1);
    }
}
