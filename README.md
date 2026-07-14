# Persica

> A modern programming language written in Java.

Persica is an experimental programming language built from scratch to learn and explore compiler and language design. The project includes its own lexer, parser, abstract syntax tree (AST), and interpreter.

---

# Version

**Current Version:** `v0.1.0`

Status: **Stable**

---

# Features

## Lexer

* Keywords
* Identifiers
* Numbers
* Strings
* Comments
* Operators
* Separators

## Parser

* Recursive Descent Parser
* AST Generation
* Operator Precedence

## Runtime

* Variable Environment
* Expression Evaluation
* Statement Execution

---

# Supported Language Features

## Variables

```persica
int age = 20;
bool ok = true;
string name = "Persica";
```

## Assignment

```persica
age = 30;
```

## Arithmetic Operators

```text
+
-
*
/
```

## Comparison Operators

```text
>
<
>=
<=
==
!=
```

## Logical Operators

```text
&&
||
!
```

## Print

```persica
print(age);
```

---

# Example

```persica
int a = 10;
int b = 20;

print(a + b);
print(a < b);
print(true && false);
```

Output

```text
30
true
false
```

---

# Project Structure

```text
src/
 ├── lexer/
 ├── parser/
 ├── ast/
 ├── runtime/
 └── main/
```

---

# Build

Run the project

```bash
./gradlew run
```

Build

```bash
./gradlew build
```

---

# Requirements

* Java 21+
* Gradle 9+

---

# License

MIT License

---

# Author

**Taha Heidary**
