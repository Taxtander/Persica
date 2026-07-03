package com.persica;

import com.persica.ast.stmt.Statement;
import com.persica.lexer.Lexer;
import com.persica.lexer.Token;
import com.persica.parser.Parser;
import com.persica.runtime.Interpreter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {

            // =========================
            // 1. Read Source File
            // =========================
            String source = Files.readString(Path.of("main.persi"));

            // =========================
            // 2. Lexer (Source → Tokens)
            // =========================
            Lexer lexer = new Lexer(source);
            List<Token> tokens = lexer.tokenize();

            System.out.println("===== TOKENS =====");
            tokens.forEach(System.out::println);

            // =========================
            // 3. Parser (Tokens → AST)
            // =========================
            Parser parser = new Parser(tokens);
            List<Statement> program = parser.parse();

            System.out.println("\n===== RUNNING =====");

            // =========================
            // 4. Interpreter (AST → Execution)
            // =========================
            Interpreter interpreter = new Interpreter();
            interpreter.interpret(program);

        } catch (IOException e) {
            System.err.println("Could not read source file: main.persi");
            e.printStackTrace();
        }

    }
}