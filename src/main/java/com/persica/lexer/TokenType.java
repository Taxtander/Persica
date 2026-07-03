package com.persica.lexer;

/**
 * Represents every token type recognized by the Persica lexer.
 */
public enum TokenType {

    // =========================================================
    // Keywords
    // =========================================================

    DEF,
    RETURN,

    IF,
    ELSE,

    WHILE,

    PRINT,

    TRUE,
    FALSE,

    NULL,

    INT,
    LONG,
    FLOAT,
    DOUBLE,

    BOOL,
    CHAR,
    STRING,



    // =========================================================
    // Literals
    // =========================================================

    IDENTIFIER,
    NUMBER,
    STRING_LITERAL,
    CHAR_LITERAL,



    // =========================================================
    // Arithmetic Operators
    // =========================================================

    PLUS,
    MINUS,
    STAR,
    SLASH,
    MODULO,

    INCREMENT,
    DECREMENT,



    // =========================================================
    // Assignment Operators
    // =========================================================

    ASSIGN,

    PLUS_ASSIGN,
    MINUS_ASSIGN,
    STAR_ASSIGN,
    SLASH_ASSIGN,
    MODULO_ASSIGN,



    // =========================================================
    // Comparison Operators
    // =========================================================

    EQUAL,
    NOT_EQUAL,

    GREATER,
    GREATER_EQUAL,

    LESS,
    LESS_EQUAL,



    // =========================================================
    // Logical Operators
    // =========================================================

    AND,
    OR,
    NOT,



    // =========================================================
    // Delimiters
    // =========================================================

    LEFT_PAREN,
    RIGHT_PAREN,

    LEFT_BRACE,
    RIGHT_BRACE,

    LEFT_BRACKET,
    RIGHT_BRACKET,

    COMMA,
    DOT,
    SEMICOLON,
    COLON,



    // =========================================================
    // Special
    // =========================================================

    EOF
}