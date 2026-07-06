package com.persica.ast.expr;

public class UnaryExpression extends Expression {

    public final String operator;
    public final Expression right;

    public UnaryExpression(String operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }
}