package com.persica.lexer;

/**
 * Reads characters from the source code.
 * This class is the only component that directly accesses the source string.
 */
public class SourceReader {

    private final String source;

    private int index;
    private int line;
    private int column;

    public SourceReader(String source) {
        this.source = source;
        this.index = 0;
        this.line = 1;
        this.column = 1;
    }

    /**
     * Returns true if the end of the source has been reached.
     */
    public boolean isAtEnd() {
        return index >= source.length();
    }

    /**
     * Returns the current character without consuming it.
     */
    public char peek() {
        if (isAtEnd()) {
            return '\0';
        }
        return source.charAt(index);
    }

    /**
     * Returns the next character without consuming it.
     */
    public char peekNext() {
        if (index + 1 >= source.length()) {
            return '\0';
        }
        return source.charAt(index + 1);
    }

    /**
     * Consumes and returns the current character.
     */
    public char advance() {

        if (isAtEnd()) {
            return '\0';
        }

        char current = source.charAt(index);

        index++;

        if (current == '\n') {
            line++;
            column = 1;
        } else {
            column++;
        }

        return current;
    }

    /**
     * Consumes the expected character if it exists.
     */
    public boolean match(char expected) {

        if (isAtEnd()) {
            return false;
        }

        if (source.charAt(index) != expected) {
            return false;
        }

        advance();

        return true;
    }

    /**
     * Returns the current line number.
     */
    public int getLine() {
        return line;
    }

    /**
     * Returns the current column number.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Returns the current source index.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns the original source code.
     */
    public String getSource() {
        return source;
    }

}