package com.persica.parser;

import com.persica.lexer.Token;
import com.persica.lexer.TokenType;
import com.persica.ast.expr.*;
import com.persica.ast.stmt.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // =========================
    // ENTRY POINT
    // =========================
    public List<Statement> parse() {

        List<Statement> statements = new ArrayList<>();

        while (!isAtEnd()) {
            statements.add(statement());
        }

        return statements;
    }

    // =========================
    // STATEMENTS
    // =========================
    private Statement statement() {

        if (match(TokenType.INT, TokenType.STRING, TokenType.BOOL)) {
            return variableDeclaration();
        }

        if (match(TokenType.PRINT)) {
            return printStatement();
        }

        return expressionStatement();
    }

    // int a = 5;
    private Statement variableDeclaration() {

        Token name = consume(TokenType.IDENTIFIER, "Expect variable name.");

        consume(TokenType.ASSIGN, "Expect '=' after variable name.");

        Expression value = expression();

        consume(TokenType.SEMICOLON, "Expect ';' after declaration.");

        return new VariableDeclaration(name.getLexeme(), value);
    }

    private Statement printStatement() {

        consume(TokenType.LEFT_PAREN, "Expect '(' after print.");

        Expression value = expression();

        consume(TokenType.RIGHT_PAREN, "Expect ')' after value.");

        consume(TokenType.SEMICOLON, "Expect ';' after print statement.");

        return new PrintStatement(value);
    }

    private Statement expressionStatement() {

        Expression expr = expression();

        consume(TokenType.SEMICOLON, "Expect ';' after expression.");

        return new ExpressionStatement(expr);
    }

    // =========================
    // EXPRESSIONS
    // =========================
    private Expression expression() {
        return logicalOr();
    }

    private Expression logicalOr() {

        Expression expr = logicalAnd();

        while (match(TokenType.OR)) {

            Token operator = previous();

            Expression right = logicalAnd();

            expr = new BinaryExpression(expr, operator.getLexeme(), right);
        }

        return expr;
    }

    private Expression logicalAnd() {

        Expression expr = equality();

        while (match(TokenType.AND)) {

            Token operator = previous();

            Expression right = equality();

            expr = new BinaryExpression(expr, operator.getLexeme(), right);
        }

        return expr;
    }

    private Expression equality() {

        Expression expr = comparison();

        while (match(TokenType.EQUAL, TokenType.NOT_EQUAL)) {

            Token operator = previous();

            Expression right = comparison();

            expr = new BinaryExpression(expr, operator.getLexeme(), right);
        }

        return expr;
    }
    private Expression comparison() {

        Expression expr = addition();

        while (
                match(
                        TokenType.GREATER,
                        TokenType.LESS,
                        TokenType.GREATER_EQUAL,
                        TokenType.LESS_EQUAL
                )
        ) {

            Token operator = previous();
            Expression right = addition();

            expr = new BinaryExpression(
                    expr,
                    operator.getLexeme(),
                    right
            );

        }

        return expr;
    }
    private Expression addition() {

        Expression expr = multiplication();

        while (match(TokenType.PLUS, TokenType.MINUS)) {

            Token operator = previous();
            Expression right = multiplication();

            expr = new BinaryExpression(expr, operator.getLexeme(), right);
        }

        return expr;
    }

    private Expression assignment() {

        Expression expr = logicalOr();

        if (match(TokenType.ASSIGN)) {

            Token equals = previous();

            Expression value = assignment();

            if (expr instanceof Identifier id) {
                return new AssignmentExpression(id.name, value);
            }

            throw new RuntimeException(
                    "Invalid assignment target near '" +
                            equals.getLexeme() + "'"
            );
        }

        return expr;
    }
    private Expression multiplication() {

        Expression expr = unary();
        while (match(TokenType.STAR, TokenType.SLASH)) {

            Token operator = previous();
            Expression right = unary();
            expr = new BinaryExpression(expr, operator.getLexeme(), right);
        }

        return expr;
    }

    private Expression primary() {


        if (match(TokenType.NUMBER)) {
            return new Literal(previous().getLexeme());
        }

        if (match(TokenType.TRUE)) {
            return new Literal(true);
        }

        if (match(TokenType.FALSE)) {
            return new Literal(false);
        }

        if (match(TokenType.IDENTIFIER)) {
            return new Identifier(previous().getLexeme());
        }

        return null;
    }

    // =========================
    // HELPERS
    // =========================
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

    private Expression unary() {

        if (match(TokenType.NOT)) {

            Token operator = previous();

            Expression right = unary();

            return new UnaryExpression(
                    operator.getLexeme(),
                    right
            );
        }

        return primary();
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

    private Token consume(TokenType type, String message) {

        if (check(type)) return advance();

        throw new RuntimeException(message);
    }
}