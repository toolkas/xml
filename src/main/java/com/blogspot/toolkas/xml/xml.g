xml: entity;

entity:
    start_tag body end_tag
    short_tag;

start_tag:
    '<' WORD '>';

end_tag:
    '<' '/' WORD '>'
    ;

body:
     entity*
     DATA
     ;

short_tag:
    '<' WORD '/' >'