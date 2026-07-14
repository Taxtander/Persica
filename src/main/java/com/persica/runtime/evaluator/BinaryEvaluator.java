package com.persica.runtime.evaluator;

public class BinaryEvaluator {

    public static Object evaluate(
            Object left,
            String operator,
            Object right
    ) {

        return switch (operator) {

            case "+" -> toNumber(left) + toNumber(right);
            case "-" -> toNumber(left) - toNumber(right);
            case "*" -> toNumber(left) * toNumber(right);
            case "/" -> toNumber(left) / toNumber(right);

            case ">" -> toNumber(left) > toNumber(right);
            case "<" -> toNumber(left) < toNumber(right);
            case ">=" -> toNumber(left) >= toNumber(right);
            case "<=" -> toNumber(left) <= toNumber(right);

            case "==" -> left.equals(right);
            case "!=" -> !left.equals(right);

            case "&&" -> (Boolean) left && (Boolean) right;
            case "||" -> (Boolean) left || (Boolean) right;

            default -> throw new RuntimeException(
                    "Unknown operator: " + operator
            );
        };

    }

    private static int toNumber(Object obj) {
        return Integer.parseInt(obj.toString());
    }

}