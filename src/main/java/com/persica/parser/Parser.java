package com.persica.parser;

import com.persica.lexer.Token;
import com.persica.lexer.TokenType;

import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void parse() {

        while (!isAtEnd()) {
            statement();
        }
    }

    private void statement() {

        if (match(TokenType.INT, TokenType.STRING, TokenType.BOOL)) {
            variableDeclaration();
        } else if (match(TokenType.PRINT)) {
            printStatement();
        } else {
            expressionStatement();
        }
    }

    private void variableDeclaration() {
        System.out.println("VAR DECL");
    }

    private void printStatement() {
        System.out.println("PRINT STMT");
    }

    private void expressionStatement() {
        System.out.println("EXPR STMT");
    }

    // ===== Helpers =====

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }
}