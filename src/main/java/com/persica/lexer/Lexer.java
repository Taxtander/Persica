package com.persica.lexer;

import java.util.ArrayList;
import java.util.List;

/**
 * Persica Lexer
 *
 * Responsible for converting source code into a list of tokens.
 *
 * Version: 0.1
 */
public class Lexer {

    private final SourceReader reader;
    private final List<Token> tokens;

    public Lexer(String source) {
        this.reader = new SourceReader(source);
        this.tokens = new ArrayList<>();
    }

    /**
     * Starts lexical analysis.
     */
    public List<Token> tokenize() {

        while (!reader.isAtEnd()) {
            scanToken();
        }

        addToken(TokenType.EOF, "");

        return tokens;
    }

    /**
     * Scans the next token.
     *
     * Currently empty.
     * Future commits will gradually implement:
     *
     * - Whitespace
     * - Identifiers
     * - Numbers
     * - Strings
     * - Operators
     * - Comments
     */
    private void scanToken() {



        char c = reader.advance();

        switch (c) {

            case ' ':
            case '\t':
            case '\r':
            case '\n':
                // Ignore whitespace
                break;

            default:
                if (isAlpha(c)) {
                    identifier();
                } else if (isDigit(c)) {
                    number();
                }
                else if (c == '"') {
                    string();
                }

                else {
                    operatorOrSymbol(c);
                }
                break;
        }



    }



    /**
     * Adds a token to the token list.
     */
    private void addToken(TokenType type, String lexeme) {

        tokens.add(
                new Token(
                        type,
                        lexeme,
                        reader.getLine(),
                        reader.getColumn()
                )
        );

    }

    private boolean isAlpha(char c) {
        return Character.isLetter(c) || c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || Character.isDigit(c);
    }

    private void identifier() {

        StringBuilder sb = new StringBuilder();

        sb.append(reader.advance()); // اولین کاراکتر

        while (!reader.isAtEnd() && isAlphaNumeric(reader.peek())) {
            sb.append(reader.advance());
        }

        String text = sb.toString();

        TokenType type = switch (text) {

            case "int" -> TokenType.INT;
            case "long" -> TokenType.LONG;
            case "float" -> TokenType.FLOAT;
            case "double" -> TokenType.DOUBLE;

            case "bool" -> TokenType.BOOL;
            case "char" -> TokenType.CHAR;
            case "string" -> TokenType.STRING;

            case "if" -> TokenType.IF;
            case "else" -> TokenType.ELSE;
            case "while" -> TokenType.WHILE;

            case "def" -> TokenType.DEF;
            case "return" -> TokenType.RETURN;

            case "print" -> TokenType.PRINT;

            case "true" -> TokenType.TRUE;
            case "false" -> TokenType.FALSE;

            case "null" -> TokenType.NULL;

            default -> TokenType.IDENTIFIER;
        };

        addToken(type, text);
    }

    private boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    private void number() {

        StringBuilder sb = new StringBuilder();

        sb.append(reader.advance());

        while (!reader.isAtEnd() && isDigit(reader.peek())) {
            sb.append(reader.advance());
        }

        // بررسی عدد اعشاری
        if (!reader.isAtEnd() && reader.peek() == '.' && isDigit(reader.peekNext())) {

            sb.append(reader.advance()); // '.'

            while (!reader.isAtEnd() && isDigit(reader.peek())) {
                sb.append(reader.advance());
            }
        }

        String text = sb.toString();

        addToken(TokenType.NUMBER, text);
    }

    private void string() {

        StringBuilder sb = new StringBuilder();

        while (!reader.isAtEnd() && reader.peek() != '"') {
            sb.append(reader.advance());
        }

        if (reader.isAtEnd()) {
            // TODO: error handling later
            return;
        }

        reader.advance(); // closing "

        String text = sb.toString();

        addToken(TokenType.STRING_LITERAL, text);
    }

    private void operatorOrSymbol(char c) {

        switch (c) {

            // ===== Single-character operators =====
            case '+' -> addToken(TokenType.PLUS, "+");
            case '-' -> addToken(TokenType.MINUS, "-");
            case '*' -> addToken(TokenType.STAR, "*");
            case '/' -> addToken(TokenType.SLASH, "/");

            case '=' -> {
                if (reader.match('=')) {
                    addToken(TokenType.EQUAL, "==");
                } else {
                    addToken(TokenType.ASSIGN, "=");
                }
            }

            case '!' -> {
                if (reader.match('=')) {
                    addToken(TokenType.NOT_EQUAL, "!=");
                }
            }

            case '>' -> {
                if (reader.match('=')) {
                    addToken(TokenType.GREATER_EQUAL, ">=");
                } else {
                    addToken(TokenType.GREATER, ">");
                }
            }

            case '<' -> {
                if (reader.match('=')) {
                    addToken(TokenType.LESS_EQUAL, "<=");
                } else {
                    addToken(TokenType.LESS, "<");
                }
            }

            // ===== Symbols =====
            case '(' -> addToken(TokenType.LEFT_PAREN, "(");
            case ')' -> addToken(TokenType.RIGHT_PAREN, ")");

            case '{' -> addToken(TokenType.LEFT_BRACE, "{");
            case '}' -> addToken(TokenType.RIGHT_BRACE, "}");

            case '[' -> addToken(TokenType.LEFT_BRACKET, "[");
            case ']' -> addToken(TokenType.RIGHT_BRACKET, "]");

            case ';' -> addToken(TokenType.SEMICOLON, ";");
            case ',' -> addToken(TokenType.COMMA, ",");
            case '.' -> addToken(TokenType.DOT, ".");

            default -> {
                // TODO: error handling later
            }
        }
    }

}