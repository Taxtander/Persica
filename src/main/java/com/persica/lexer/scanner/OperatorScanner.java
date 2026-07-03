package com.persica.lexer.scanner;

import com.persica.lexer.LexerContext;
import com.persica.lexer.TokenType;

public class OperatorScanner {

    private final LexerContext context;

    public OperatorScanner(LexerContext context) {
        this.context = context;
    }

    public void scan(char c) {

        switch (c) {

            case '+' -> context.add(TokenType.PLUS, "+");

            case '-' -> context.add(TokenType.MINUS, "-");

            case '*' -> context.add(TokenType.STAR, "*");

            case '/' -> context.add(TokenType.SLASH, "/");

            case '=' -> {

                if (context.reader().match('=')) {
                    context.add(TokenType.EQUAL, "==");
                } else {
                    context.add(TokenType.ASSIGN, "=");
                }

            }

            case '!' -> {

                if (context.reader().match('=')) {
                    context.add(TokenType.NOT_EQUAL, "!=");
                }

            }

            case '>' -> {

                if (context.reader().match('=')) {
                    context.add(TokenType.GREATER_EQUAL, ">=");
                } else {
                    context.add(TokenType.GREATER, ">");
                }

            }

            case '<' -> {

                if (context.reader().match('=')) {
                    context.add(TokenType.LESS_EQUAL, "<=");
                } else {
                    context.add(TokenType.LESS, "<");
                }

            }

            case '(' -> context.add(TokenType.LEFT_PAREN, "(");
            case ')' -> context.add(TokenType.RIGHT_PAREN, ")");

            case '{' -> context.add(TokenType.LEFT_BRACE, "{");
            case '}' -> context.add(TokenType.RIGHT_BRACE, "}");

            case '[' -> context.add(TokenType.LEFT_BRACKET, "[");
            case ']' -> context.add(TokenType.RIGHT_BRACKET, "]");

            case ';' -> context.add(TokenType.SEMICOLON, ";");
            case ',' -> context.add(TokenType.COMMA, ",");
            case '.' -> context.add(TokenType.DOT, ".");

        }

    }

}