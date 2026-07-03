package com.persica.ast.expr;

public class Identifier extends Expression {

    public final String name;

    public Identifier(String name) {
        this.name = name;
    }
}