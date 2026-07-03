package com.persica.lexer;

import com.persica.lexer.scanner.IdentifierScanner;
import com.persica.lexer.scanner.NumberScanner;
import com.persica.lexer.scanner.OperatorScanner;
import com.persica.lexer.scanner.StringScanner;
import com.persica.lexer.scanner.WhitespaceScanner;
import com.persica.lexer.util.CharUtil;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private final SourceReader reader;
    private final List<Token> tokens;

    private final LexerContext context;

    private final IdentifierScanner identifierScanner;
    private final NumberScanner numberScanner;
    private final StringScanner stringScanner;
    private final OperatorScanner operatorScanner;
    private final WhitespaceScanner whitespaceScanner;

    public Lexer(String source) {

        this.reader = new SourceReader(source);
        this.tokens = new ArrayList<>();

        this.context = new LexerContext(reader, tokens);

        this.identifierScanner = new IdentifierScanner(context);
        this.numberScanner = new NumberScanner(context);
        this.stringScanner = new StringScanner(context);
        this.operatorScanner = new OperatorScanner(context);
        this.whitespaceScanner = new WhitespaceScanner();

    }

    /**
     * Starts lexical analysis.
     */
    public List<Token> tokenize() {

        while (!reader.isAtEnd()) {
            scanToken();
        }

        context.add(TokenType.EOF, "");

        return tokens;

    }

    /**
     * Scan one token.
     */
    private void scanToken() {

        char c = reader.advance();

        // Ignore whitespace
        if (whitespaceScanner.scan(c)) {
            return;
        }

        // Identifier / Keyword
        if (CharUtil.isAlpha(c)) {
            identifierScanner.scan(c);
            return;
        }

        // Number
        if (CharUtil.isDigit(c)) {
            numberScanner.scan(c);
            return;
        }

        if (c == '"') {
            stringScanner.scan();
            return;
        }

        // Operators / Symbols
        operatorScanner.scan(c);

    }

}