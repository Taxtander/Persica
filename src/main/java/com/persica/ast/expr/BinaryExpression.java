package com.persica.ast.expr;

public class BinaryExpression extends Expression {

    public final Expression left;
    public final Expression right;
    public final String operator;

    public BinaryExpression(Expression left, String operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
}