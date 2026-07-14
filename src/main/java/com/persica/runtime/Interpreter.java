package com.persica.runtime;

import com.persica.ast.stmt.Statement;
import com.persica.runtime.executor.StatementExecutor;

import java.util.List;

public class Interpreter {

    private final Environment env = new Environment();
    private final StatementExecutor executor = new StatementExecutor(env);

    public void interpret(List<Statement> statements) {

        for (Statement stmt : statements) {
            executor.execute(stmt);
        }

    }

}