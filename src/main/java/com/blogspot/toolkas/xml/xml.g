xml: document;

document: element;

element:
    '<' NAME attribute* '>' content '<' '/' NAME '>'
    '<' NAME attribute* '/' '>';

content:
     element*
     TEXT
     ;

attribute:
    name '=' STRING;