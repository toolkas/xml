package com.blogspot.toolkas.xml.parser;

import com.blogspot.toolkas.xml.lexer.Lexem;
import com.blogspot.toolkas.xml.lexer.TokenizeException;
import com.blogspot.toolkas.xml.lexer.XmlLexer;
import com.blogspot.toolkas.xml.model.Document;
import com.blogspot.toolkas.xml.model.Element;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class XmlParserTest {
    @Test
    public void testParse() throws TokenizeException, XmlParseException {
        XmlLexer lexer = new XmlLexer("<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
                "    <modelVersion>4.0.0</modelVersion>\n" +
                "\n" +
                "    <groupId>com.blogspot.toolkas</groupId>\n" +
                "    <artifactId>xml</artifactId>\n" +
                "    <version>1.0-SNAPSHOT</version>\n" +
                "    <name>xml</name>\n" +
                "\n" +
                "    <build>\n" +
                "        <plugins>\n" +
                "            <plugin>\n" +
                "                <groupId>org.apache.maven.plugins</groupId>\n" +
                "                <artifactId>maven-compiler-plugin</artifactId>\n" +
                "                <configuration>\n" +
                "                    <source>1.8</source>\n" +
                "                    <target>1.8</target>\n" +
                "                </configuration>\n" +
                "            </plugin>\n" +
                "        </plugins>\n" +
                "    </build>\n" +
                "\n" +
                "    <dependencies>\n" +
                "        <dependency>\n" +
                "            <groupId>junit</groupId>\n" +
                "            <artifactId>junit</artifactId>\n" +
                "            <version>4.12</version>\n" +
                "            <scope>test</scope>\n" +
                "        </dependency>\n" +
                "    </dependencies>\n" +
                "</project>");
        List<Lexem> lexems = lexer.tokenize();

        XmlParser parser = new XmlParser();
        Document document = parser.parse(lexems);

        Element root = document.getElement();
        Assert.assertEquals("project", root.getName());
        Assert.assertEquals("project", root.getName());
    }
}
