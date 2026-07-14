package com.persica.parser.statement;

import com.persica.ast.expr.Expression;
import com.persica.ast.stmt.*;
import com.persica.lexer.Token;
import com.persica.lexer.TokenType;
import com.persica.parser.ParserContext;
import com.persica.parser.expression.ExpressionParser;

public class StatementParser {

    private final ParserContext context;
    private final ExpressionParser expressionParser;

    public StatementParser(
            ParserContext context,
            ExpressionParser expressionParser
    ) {
        this.context = context;
        this.expressionParser = expressionParser;
    }

    public Statement statement() {

        if (context.match(
                TokenType.INT,
                TokenType.STRING,
                TokenType.BOOL
        )) {
            return variableDeclaration();
        }

        if (context.match(TokenType.PRINT)) {
            return printStatement();
        }

        return expressionStatement();
    }

    private Statement variableDeclaration() {

        Token name = context.consume(
                TokenType.IDENTIFIER,
                "Expect variable name."
        );

        context.consume(
                TokenType.ASSIGN,
                "Expect '=' after variable name."
        );

        Expression value = expressionParser.expression();

        context.consume(
                TokenType.SEMICOLON,
                "Expect ';' after declaration."
        );

        return new VariableDeclaration(
                name.getLexeme(),
                value
        );
    }

    private Statement printStatement() {

        context.consume(
                TokenType.LEFT_PAREN,
                "Expect '(' after print."
        );

        Expression value = expressionParser.expression();

        context.consume(
                TokenType.RIGHT_PAREN,
                "Expect ')' after value."
        );

        context.consume(
                TokenType.SEMICOLON,
                "Expect ';' after print statement."
        );

        return new PrintStatement(value);
    }

    private Statement expressionStatement() {

        Expression expr = expressionParser.expression();

        context.consume(
                TokenType.SEMICOLON,
                "Expect ';' after expression."
        );

        return new ExpressionStatement(expr);
    }

}