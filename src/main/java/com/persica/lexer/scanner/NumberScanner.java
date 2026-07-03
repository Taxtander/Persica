package com.persica.lexer.scanner;

import com.persica.lexer.LexerContext;
import com.persica.lexer.TokenType;
import com.persica.lexer.util.CharUtil;

public class NumberScanner {

    private final LexerContext context;

    public NumberScanner(LexerContext context) {
        this.context = context;
    }

    public void scan(char firstChar) {

        StringBuilder sb = new StringBuilder();

        sb.append(firstChar);

        while (!context.reader().isAtEnd()
                && CharUtil.isDigit(context.reader().peek())) {

            sb.append(context.reader().advance());

        }

        if (!context.reader().isAtEnd()
                && context.reader().peek() == '.'
                && CharUtil.isDigit(context.reader().peekNext())) {

            sb.append(context.reader().advance());

            while (!context.reader().isAtEnd()
                    && CharUtil.isDigit(context.reader().peek())) {

                sb.append(context.reader().advance());

            }

        }

        context.add(TokenType.NUMBER, sb.toString());

    }

}