package com.persica.parser;

import com.persica.ast.stmt.Statement;
import com.persica.lexer.Token;
import com.persica.parser.expression.ExpressionParser;
import com.persica.parser.statement.StatementParser;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final ParserContext context;

    private final ExpressionParser expressionParser;

    private final StatementParser statementParser;

    public Parser(List<Token> tokens) {

        this.context = new ParserContext(tokens);

        this.expressionParser = new ExpressionParser(context);

        this.statementParser = new StatementParser(
                context,
                expressionParser
        );

    }

    public List<Statement> parse() {

        List<Statement> statements = new ArrayList<>();

        while (!context.isAtEnd()) {
            statements.add(statementParser.statement());
        }

        return statements;
    }

}