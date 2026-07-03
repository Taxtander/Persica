package com.persica.ast.expr;

public class Literal extends Expression {

    public final Object value;

    public Literal(Object value) {
        this.value = value;
    }
}