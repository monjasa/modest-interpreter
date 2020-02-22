package org.monjasa.interpreter.engine;

import org.monjasa.interpreter.engine.tokens.Token;
import org.monjasa.interpreter.engine.tokens.TokenType;

public class Lexer {

    private static final char END_OF_COMMAND = 0;

    private String command;
    private int position;
    private char currentChar;

    public Lexer(String command) {
        this.command = command;
        this.position = 0;
        this.currentChar = command.charAt(position);
    }

    public Token getNextToken() {

        while (currentChar != END_OF_COMMAND) {

            if (Character.isWhitespace(currentChar)) {
                skipWhitespaces();
                continue;
            }

            if (Character.isDigit(currentChar)) {
                return new Token(TokenType.INTEGER, getIntegerString());
            }

            switch (currentChar) {
                case '+':
                    advancePointer();
                    return new Token(TokenType.ADDITION, "+");
                case '-':
                    advancePointer();
                    return new Token(TokenType.SUBTRACTION, "-");
                case '*':
                    advancePointer();
                    return new Token(TokenType.MULTIPLICATION, "*");
                case '/':
                    advancePointer();
                    return new Token(TokenType.DIVISION, "/");
                case '(':
                    advancePointer();
                    return new Token(TokenType.LEFT_PARENTHESIS, "(");
                case ')':
                    advancePointer();
                    return new Token(TokenType.RIGHT_PARENTHESIS, ")");
            }
        }

        return new Token(TokenType.EOF, String.valueOf(END_OF_COMMAND));
    }

    public String getIntegerString() {

        StringBuilder integerString = new StringBuilder();
        while (currentChar != END_OF_COMMAND && Character.isDigit(currentChar)) {
            integerString.append(currentChar);
            advancePointer();
        }

        return integerString.toString();
    }

    public void advancePointer() {
        position++;
        if (position == command.length()) currentChar = END_OF_COMMAND;
        else currentChar = command.charAt(position);
    }

    public void skipWhitespaces() {

        while (currentChar != END_OF_COMMAND && Character.isWhitespace(currentChar))
            advancePointer();
    }
}
