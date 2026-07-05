package com.persica.ast.expr;

public class AssignmentExpression extends Expression {

    public final String name;
    public final Expression value;

    public AssignmentExpression(String name, Expression value) {
        this.name = name;
        this.value = value;
    }
}