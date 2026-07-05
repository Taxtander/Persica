package com.persica.runtime;

import com.persica.ast.expr.*;
import com.persica.ast.stmt.*;

import java.util.List;

public class Interpreter {

    private final Environment env = new Environment();

    // =========================
    // ENTRY POINT
    // =========================
    public void interpret(List<Statement> statements) {

        for (Statement stmt : statements) {
            execute(stmt);
        }
    }

    // =========================
    // STATEMENTS
    // =========================
    private void execute(Statement stmt) {

        if (stmt instanceof VariableDeclaration var) {
            executeVariable(var);
        }

        else if (stmt instanceof PrintStatement print) {
            executePrint(print);
        }

        else if (stmt instanceof ExpressionStatement expr) {
            evaluate(expr.expression);
        }
    }

    private void executeVariable(VariableDeclaration var) {

        Object value = evaluate(var.value);

        env.define(var.name, value);
    }

    private void executePrint(PrintStatement print) {

        Object value = evaluate(print.value);

        System.out.println(value);
    }

    // =========================
    // EXPRESSIONS
    // =========================
    private Object evaluate(Expression expr) {

        if (expr instanceof Literal lit) {
            return lit.value;
        }

        if (expr instanceof Identifier id) {
            return env.get(id.name);
        }

        if (expr instanceof AssignmentExpression assign) {

            Object value = evaluate(assign.value);

            env.assign(assign.name, value);

            return value;
        }

        if (expr instanceof BinaryExpression bin) {

            Object left = evaluate(bin.left);
            Object right = evaluate(bin.right);

            return switch (bin.operator) {

                case "+" -> toNumber(left) + toNumber(right);
                case "-" -> toNumber(left) - toNumber(right);
                case "*" -> toNumber(left) * toNumber(right);
                case "/" -> toNumber(left) / toNumber(right);

                default -> null;
            };
        }

        return null;
    }

    private int toNumber(Object obj) {
        return Integer.parseInt(obj.toString());
    }
}