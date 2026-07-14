package com.persica.runtime.evaluator;

public class UnaryEvaluator {

    public static Object evaluate(
            String operator,
            Object value
    ) {

        return switch (operator) {

            case "!" -> !(Boolean) value;

            case "-" -> -Integer.parseInt(value.toString());

            default -> throw new RuntimeException(
                    "Unknown unary operator: " + operator
            );

        };

    }

}