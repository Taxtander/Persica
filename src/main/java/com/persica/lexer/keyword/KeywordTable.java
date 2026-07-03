package com.persica.lexer.keyword;

import com.persica.lexer.TokenType;

import java.util.HashMap;
import java.util.Map;

public final class KeywordTable {

    private static final Map<String, TokenType> KEYWORDS = new HashMap<>();

    static {

        // Types
        KEYWORDS.put("int", TokenType.INT);
        KEYWORDS.put("long", TokenType.LONG);
        KEYWORDS.put("float", TokenType.FLOAT);
        KEYWORDS.put("double", TokenType.DOUBLE);

        KEYWORDS.put("bool", TokenType.BOOL);
        KEYWORDS.put("char", TokenType.CHAR);
        KEYWORDS.put("string", TokenType.STRING);

        // Flow
        KEYWORDS.put("if", TokenType.IF);
        KEYWORDS.put("else", TokenType.ELSE);
        KEYWORDS.put("while", TokenType.WHILE);

        // Functions
        KEYWORDS.put("def", TokenType.DEF);
        KEYWORDS.put("return", TokenType.RETURN);

        // Built-in
        KEYWORDS.put("print", TokenType.PRINT);

        // Literals
        KEYWORDS.put("true", TokenType.TRUE);
        KEYWORDS.put("false", TokenType.FALSE);
        KEYWORDS.put("null", TokenType.NULL);
    }

    private KeywordTable() {
    }

    public static TokenType lookup(String word) {
        return KEYWORDS.getOrDefault(word, TokenType.IDENTIFIER);
    }

}