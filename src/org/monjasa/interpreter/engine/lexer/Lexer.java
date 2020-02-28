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

        Token beginToken = new Token(TokenType.BEGIN, "do");
        Token endToken = new Token(TokenType.END, "end");

        temporaryMap.put(beginToken.getValue(String.class).orElseThrow(RuntimeException::new), beginToken);
        temporaryMap.put(endToken.getValue(String.class).orElseThrow(RuntimeException::new), endToken);

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
            }

            else if (Character.isDigit(currentChar)) {
                // TODO : replace Integer with wild class a.k.a. ? extends Number
                return new Token(TokenType.INTEGER, Integer.parseInt(getIntegerString()));
            }

            else if (Character.isLetter(currentChar)) {
                return getIdToken();
            }

            else {
                Token token = new Token(TokenType.getTypeByContraction(currentChar));
                advancePointer();
                return token;
            }
        }

        return new Token(TokenType.EOF);
    }

    private String getIntegerString() {

        StringBuilder integerString = new StringBuilder();
        while (currentChar != END_OF_COMMAND && Character.isDigit(currentChar)) {
            integerString.append(currentChar);
            advancePointer();
        }

        return integerString.toString();
    }

    private Token getIdToken() {

        StringBuilder name = new StringBuilder();
        while (currentChar != END_OF_COMMAND && Character.isLetterOrDigit(currentChar)) {
            name.append(currentChar);
            advancePointer();
        }

        return RESERVED_KEYWORDS.getOrDefault(name.toString(), new Token(TokenType.ID, name.toString()));
    }

    private void advancePointer() {
        position++;
        if (position == command.length()) currentChar = END_OF_COMMAND;
        else currentChar = command.charAt(position);
    }

    private void skipWhitespaces() {

        while (currentChar != END_OF_COMMAND && Character.isWhitespace(currentChar))
            advancePointer();
    }

//    public char peekCharacter() {
//        int peekPosition = position + 1;
//        if (peekPosition == command.length()) return END_OF_COMMAND;
//        else return command.charAt(peekPosition);
//    }

}
