package com.blogspot.toolkas.xml;

import com.blogspot.toolkas.xml.lexer.Lexem;
import com.blogspot.toolkas.xml.lexer.TokenizeException;
import com.blogspot.toolkas.xml.lexer.XmlLexer;
import com.blogspot.toolkas.xml.model.Document;
import com.blogspot.toolkas.xml.model.Element;
import com.blogspot.toolkas.xml.parser.XmlParseException;
import com.blogspot.toolkas.xml.parser.XmlParser;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Example {
    public static void main(String[] args) throws IOException, TokenizeException, XmlParseException {
        try(InputStream input = Example.class.getResourceAsStream("example.xml")) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            IOUtils.copy(input, output);

            String xml = output.toString("UTF-8");
            XmlLexer lexer = new XmlLexer(xml);

            List<Lexem> lexems = lexer.tokenize();
            XmlParser parser = new XmlParser();
            Document document = parser.parse(lexems);
            Element project = document.getElement();

            project.getChildren().stream()
                    .filter(element -> "groupId".equals(element.getName())).findAny()
                    .ifPresent(
                            element -> System.out.println(element.getText())
                    );
        }
    }
}
