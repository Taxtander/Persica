package com.persica.lexer.scanner;

import com.persica.lexer.LexerContext;
import com.persica.lexer.TokenType;
import com.persica.lexer.keyword.KeywordTable;
import com.persica.lexer.util.CharUtil;

public class IdentifierScanner {

    private final LexerContext context;

    public IdentifierScanner(LexerContext context) {
        this.context = context;
    }

    public void scan(char firstChar) {

        StringBuilder sb = new StringBuilder();

        sb.append(firstChar);

        while (!context.reader().isAtEnd()
                && CharUtil.isAlphaNumeric(context.reader().peek())) {

            sb.append(context.reader().advance());

        }

        String text = sb.toString();

        TokenType type = KeywordTable.lookup(text);

        context.add(type, text);

    }

}