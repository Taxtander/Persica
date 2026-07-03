package com.persica.lexer;

import java.util.ArrayList;
import java.util.List;

import com.persica.lexer.util.CharUtil;
import com.persica.lexer.keyword.KeywordTable;

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

        char c = reader.peek(); // ❗ فقط نگاه کن، مصرف نکن

        if (reader.isAtEnd()) return;

        // ===== whitespace =====
        if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
            reader.advance();
            return;
        }

        // ===== identifier / keyword =====
        if (CharUtil.isAlpha(c)) {
            identifier();
            return;
        }

        // ===== number =====
        if (CharUtil.isDigit(c)) {
            number();
            return;
        }

        // ===== string =====
        if (c == '"') {
            reader.advance(); // consume "
            string();
            return;
        }

        // ===== operators & symbols =====
        reader.advance();
        operatorOrSymbol(c);
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



    private void identifier() {

        StringBuilder sb = new StringBuilder();

        // ❗ مهم: چون char اول قبلاً consume شده در scanToken

        while (!reader.isAtEnd() && CharUtil.isAlphaNumeric(reader.peek())) {
            sb.append(reader.advance());
        }

        String text = sb.toString();

        TokenType type = KeywordTable.lookup(text);
        addToken(type, text);
    }


    private void number() {

        StringBuilder sb = new StringBuilder();

        sb.append(reader.advance());

        while (!reader.isAtEnd() && CharUtil.isDigit(reader.peek())) {
            sb.append(reader.advance());
        }

        // بررسی عدد اعشاری
        if (!reader.isAtEnd() && reader.peek() == '.' && CharUtil.isDigit(reader.peekNext())) {

            sb.append(reader.advance()); // '.'

            while (!reader.isAtEnd() && CharUtil.isDigit(reader.peek())) {
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
            throw new RuntimeException("Unterminated string");
        }

        reader.advance(); // closing "

        addToken(TokenType.STRING_LITERAL, sb.toString());
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