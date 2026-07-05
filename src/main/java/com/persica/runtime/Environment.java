package com.persica.runtime;

import java.util.HashMap;
import java.util.Map;

public class Environment {

    private final Map<String, Object> variables = new HashMap<>();

    public void define(String name, Object value) {
        variables.put(name, value);
    }

    public void assign(String name, Object value) {

        if (!variables.containsKey(name)) {
            throw new RuntimeException("Undefined variable: " + name);
        }

        variables.put(name, value);
    }

    public Object get(String name) {
        return variables.get(name);
    }
}