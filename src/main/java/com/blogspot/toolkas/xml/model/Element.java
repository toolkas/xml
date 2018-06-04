package com.blogspot.toolkas.xml.model;

import java.util.ArrayList;
import java.util.List;

public class Element {
    private final String name;
    private final List<Attribute> attributes = new ArrayList<>();
    private String text;

    private final List<Element> children = new ArrayList<>();

    public Element(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void add(Attribute attribute) {
        attributes.add(attribute);
    }

    public void addChild(Element element) {
        children.add(element);
    }

    public List<Element> getChildren() {
        return children;
    }
}
