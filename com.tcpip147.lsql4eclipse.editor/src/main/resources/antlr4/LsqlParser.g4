parser grammar LsqlParser;

options { tokenVocab=LsqlLexer; }

statements
    : statement (Br statement)*
    ;

statement
    : header Br body Br
    ;

header
    : idAttribute optionAttribute*
    ;

idAttribute
    : IdSymbol Ws Value Br
    ;

optionAttribute
    : OptionSymbol Ws? Value? Br
    ;

body
    : (Line | Br | Ws)*
    ;