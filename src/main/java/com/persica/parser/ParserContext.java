package com.persica.parser;

import com.persica.lexer.Token;
import com.persica.lexer.TokenType;

import java.util.List;

public class ParserContext {

    private final List<Token> tokens;
    private int current = 0;

    public ParserContext(List<Token> tokens) {
        this.tokens = tokens;
    }

    public boolean match(TokenType... types) {

        for (TokenType type : types) {

            if (check(type)) {
                advance();
                return true;
            }

        }

        return false;
    }

    public boolean check(TokenType type) {

        if (isAtEnd()) {
            return false;
        }

        return peek().getType() == type;
    }

    public Token advance() {

        if (!isAtEnd()) {
            current++;
        }

        return previous();
    }

    public boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    public Token peek() {
        return tokens.get(current);
    }

    public Token previous() {
        return tokens.get(current - 1);
    }

    public Token consume(TokenType type, String message) {

        if (check(type)) {
            return advance();
        }

        throw new RuntimeException(message);
    }

}