package com.persica.lexer.util;

public final class CharUtil {

    private CharUtil() {
    }

    public static boolean isAlpha(char c) {
        return Character.isLetter(c) || c == '_';
    }

    public static boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    public static boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

}