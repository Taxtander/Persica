package com.persica.runtime.evaluator;

import com.persica.ast.expr.*;
import com.persica.runtime.Environment;

public class ExpressionEvaluator {

    private final Environment env;

    public ExpressionEvaluator(Environment env) {
        this.env = env;
    }

    public Object evaluate(Expression expr) {

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

        if (expr instanceof UnaryExpression unary) {

            Object value = evaluate(unary.right);

            return UnaryEvaluator.evaluate(
                    unary.operator,
                    value
            );
        }

        if (expr instanceof BinaryExpression bin) {

            Object left = evaluate(bin.left);
            Object right = evaluate(bin.right);

            return BinaryEvaluator.evaluate(
                    left,
                    bin.operator,
                    right
            );
        }

        return null;
    }

}