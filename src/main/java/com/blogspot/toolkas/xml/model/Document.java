package com.blogspot.toolkas.xml.model;

public class Document {
    private final Element element;

    public Document(Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
