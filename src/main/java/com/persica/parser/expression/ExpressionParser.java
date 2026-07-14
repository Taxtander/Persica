package com.persica.parser.expression;

import com.persica.ast.expr.*;
import com.persica.lexer.Token;
import com.persica.lexer.TokenType;
import com.persica.parser.ParserContext;

public class ExpressionParser {

    private final ParserContext context;

    public ExpressionParser(ParserContext context) {
        this.context = context;
    }

    public Expression expression() {
        return assignment();
    }

    private Expression assignment() {

        Expression expr = logicalOr();

        if (context.match(TokenType.ASSIGN)) {

            Token equals = context.previous();

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

    private Expression logicalOr() {

        Expression expr = logicalAnd();

        while (context.match(TokenType.OR)) {

            Token operator = context.previous();

            Expression right = logicalAnd();

            expr = new BinaryExpression(
                    expr,
                    operator.getLexeme(),
                    right
            );
        }

        return expr;
    }

    private Expression logicalAnd() {

        Expression expr = equality();

        while (context.match(TokenType.AND)) {

            Token operator = context.previous();

            Expression right = equality();

            expr = new BinaryExpression(
                    expr,
                    operator.getLexeme(),
                    right
            );
        }

        return expr;
    }

    private Expression equality() {

        Expression expr = comparison();

        while (context.match(
                TokenType.EQUAL,
                TokenType.NOT_EQUAL
        )) {

            Token operator = context.previous();

            Expression right = comparison();

            expr = new BinaryExpression(
                    expr,
                    operator.getLexeme(),
                    right
            );
        }

        return expr;
    }

    private Expression comparison() {

        Expression expr = addition();

        while (context.match(
                TokenType.GREATER,
                TokenType.GREATER_EQUAL,
                TokenType.LESS,
                TokenType.LESS_EQUAL
        )) {

            Token operator = context.previous();

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

        while (context.match(
                TokenType.PLUS,
                TokenType.MINUS
        )) {

            Token operator = context.previous();

            Expression right = multiplication();

            expr = new BinaryExpression(
                    expr,
                    operator.getLexeme(),
                    right
            );
        }

        return expr;
    }

    private Expression multiplication() {

        Expression expr = unary();

        while (context.match(
                TokenType.STAR,
                TokenType.SLASH
        )) {

            Token operator = context.previous();

            Expression right = unary();

            expr = new BinaryExpression(
                    expr,
                    operator.getLexeme(),
                    right
            );
        }

        return expr;
    }

    private Expression unary() {

        if (context.match(
                TokenType.NOT,
                TokenType.MINUS
        )) {

            Token operator = context.previous();

            Expression right = unary();

            return new UnaryExpression(
                    operator.getLexeme(),
                    right
            );
        }

        return primary();
    }

    private Expression primary() {

        if (context.match(TokenType.LEFT_PAREN)) {

            Expression expr = expression();

            context.consume(
                    TokenType.RIGHT_PAREN,
                    "Expect ')' after expression."
            );

            return expr;
        }

        if (context.match(TokenType.NUMBER)) {
            return new Literal(context.previous().getLexeme());
        }

        if (context.match(TokenType.TRUE)) {
            return new Literal(true);
        }

        if (context.match(TokenType.FALSE)) {
            return new Literal(false);
        }

        if (context.match(TokenType.IDENTIFIER)) {
            return new Identifier(context.previous().getLexeme());
        }

        throw new RuntimeException("Expected expression.");
    }

}