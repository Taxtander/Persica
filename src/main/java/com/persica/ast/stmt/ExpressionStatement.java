package com.persica.ast.stmt;

import com.persica.ast.expr.Expression;

public class ExpressionStatement extends Statement {

    public final Expression expression;

    public ExpressionStatement(Expression expression) {
        this.expression = expression;
    }
}