package com.blogspot.toolkas.xml.lexer;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class XmlLexerTest {
    @Test
    public void testXML() throws TokenizeException {
        XmlLexer lexer = new XmlLexer("<root name=\"aaa\">TEST TEST</root>");
        List<Lexem> lexems = lexer.tokenize();

        Assert.assertEquals(12, lexems.size());

        Assert.assertEquals(Token.LT, lexems.get(0).getToken());
        Assert.assertEquals("<", lexems.get(0).getValue());

        Assert.assertEquals(Token.NAME, lexems.get(1).getToken());
        Assert.assertEquals("root", lexems.get(1).getValue());

        Assert.assertEquals(Token.NAME, lexems.get(2).getToken());
        Assert.assertEquals("name", lexems.get(2).getValue());

        Assert.assertEquals(Token.EQ, lexems.get(3).getToken());
        Assert.assertEquals("=", lexems.get(3).getValue());

        Assert.assertEquals(Token.STRING, lexems.get(4).getToken());
        Assert.assertEquals("aaa", lexems.get(4).getValue());

        Assert.assertEquals(Token.GT, lexems.get(5).getToken());
        Assert.assertEquals(">", lexems.get(5).getValue());

        Assert.assertEquals(Token.TEXT, lexems.get(6).getToken());
        Assert.assertEquals("TEST TEST", lexems.get(6).getValue());

        Assert.assertEquals(Token.LT, lexems.get(7).getToken());
        Assert.assertEquals("<", lexems.get(7).getValue());

        Assert.assertEquals(Token.SLASH, lexems.get(8).getToken());
        Assert.assertEquals("/", lexems.get(8).getValue());

        Assert.assertEquals(Token.NAME, lexems.get(9).getToken());
        Assert.assertEquals("root", lexems.get(9).getValue());

        Assert.assertEquals(Token.GT, lexems.get(10).getToken());
        Assert.assertEquals(">", lexems.get(10).getValue());

        Assert.assertEquals(Token.EOF, lexems.get(11).getToken());
    }
}
