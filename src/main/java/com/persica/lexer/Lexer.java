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

            default:
                // TODO: implement lexer rules
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

}