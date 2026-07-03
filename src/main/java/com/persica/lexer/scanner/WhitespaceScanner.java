package com.persica.lexer.scanner;

public class WhitespaceScanner {

    public boolean scan(char c) {
        return c == ' '
                || c == '\t'
                || c == '\r'
                || c == '\n';
    }

}