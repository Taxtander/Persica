package com.persica.lexer;

import java.util.List;

public class LexerContext {

    private final SourceReader reader;
    private final List<Token> tokens;

    public LexerContext(SourceReader reader, List<Token> tokens) {

        this.reader = reader;
        this.tokens = tokens;

    }

    public SourceReader reader() {
        return reader;
    }

    public void add(TokenType type, String lexeme) {

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