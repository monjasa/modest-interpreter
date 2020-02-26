package org.monjasa.interpreter.engine.lexer;

import org.monjasa.interpreter.engine.tokens.Token;
import org.monjasa.interpreter.engine.tokens.TokenType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Lexer {

    private static final char END_OF_COMMAND = 0;

    private static final Map<String, Token> RESERVED_KEYWORDS;

    static {
        HashMap<String, Token> temporaryMap = new HashMap<>();

        Token beginToken = new Token(TokenType.BEGIN, "BEGIN");
        Token endToken = new Token(TokenType.END, "END");

        temporaryMap.put(beginToken.getValue(), beginToken);
        temporaryMap.put(endToken.getValue(), endToken);

        RESERVED_KEYWORDS = Collections.unmodifiableMap(temporaryMap);
    }

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

            if (Character.isLetter(currentChar)) {
                return getIdToken();
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
                case ':':
                    if (peekCharacter() == '=');
                    advancePointer();
                    advancePointer();
                    return new Token(TokenType.ASSIGNMENT, ":=");
                case ';':
                    advancePointer();
                    return new Token(TokenType.SEMICOLON, ";");
                case '.':
                    advancePointer();
                    return new Token(TokenType.DOT, ".");
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

    public Token getIdToken() {

        StringBuilder name = new StringBuilder();
        while (currentChar != END_OF_COMMAND && Character.isLetterOrDigit(currentChar)) {
            name.append(currentChar);
            advancePointer();
        }

        return RESERVED_KEYWORDS.getOrDefault(name.toString(), new Token(TokenType.ID, name.toString()));
    }

    public char peekCharacter() {
        int peekPosition = position + 1;
        if (peekPosition == command.length()) return END_OF_COMMAND;
        else return command.charAt(peekPosition);
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
