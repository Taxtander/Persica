package com.persica.ast.stmt;

import com.persica.ast.expr.Expression;

public class PrintStatement extends Statement {

    public final Expression value;

    public PrintStatement(Expression value) {
        this.value = value;
    }
}