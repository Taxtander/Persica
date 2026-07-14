package com.persica.runtime.executor;

import com.persica.ast.stmt.*;
import com.persica.runtime.Environment;
import com.persica.runtime.evaluator.ExpressionEvaluator;

public class StatementExecutor {

    private final Environment env;
    private final ExpressionEvaluator evaluator;

    public StatementExecutor(Environment env) {
        this.env = env;
        this.evaluator = new ExpressionEvaluator(env);
    }

    public void execute(Statement stmt) {

        if (stmt instanceof VariableDeclaration var) {
            executeVariable(var);
        }

        else if (stmt instanceof PrintStatement print) {
            executePrint(print);
        }

        else if (stmt instanceof ExpressionStatement expr) {
            evaluator.evaluate(expr.expression);
        }

    }

    private void executeVariable(VariableDeclaration var) {

        Object value = evaluator.evaluate(var.value);

        env.define(var.name, value);

    }

    private void executePrint(PrintStatement print) {

        Object value = evaluator.evaluate(print.value);

        System.out.println(value);

    }

}