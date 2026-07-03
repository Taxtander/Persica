package com.persica.lexer.scanner;

import com.persica.lexer.LexerContext;
import com.persica.lexer.TokenType;

public class StringScanner {

    private final LexerContext context;

    public StringScanner(LexerContext context) {
        this.context = context;
    }

    public void scan() {

        StringBuilder sb = new StringBuilder();

        while (!context.reader().isAtEnd()
                && context.reader().peek() != '"') {

            sb.append(context.reader().advance());

        }

        if (context.reader().isAtEnd()) {
            return;
        }

        context.reader().advance();

        context.add(TokenType.STRING_LITERAL, sb.toString());
    }

}