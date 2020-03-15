package org.monjasa.interpreter.engine.lexer;

import org.monjasa.interpreter.engine.exceptions.LexerOperationException;
import org.monjasa.interpreter.engine.exceptions.MissingTokenTypeException;
import org.monjasa.interpreter.engine.tokens.Token;
import org.monjasa.interpreter.engine.tokens.TokenType;

import java.util.*;

public class Lexer {

    private static final char END_OF_COMMAND = 0;
    private static final char COMMENT_CHARACTER = '`';

    private static final Map<String, Token> RESERVED_KEYWORDS;

    static {
        Map<String, Token> temporaryMap = new HashMap<>();
        TokenType.getKeywords().forEach(tokenType -> temporaryMap.put(tokenType.getContraction(), new Token(tokenType, tokenType.getContraction())));
        RESERVED_KEYWORDS = Collections.unmodifiableMap(temporaryMap);
    }


    private String command;
    private int position;
    private char currentChar;

    private int currentLine;
    private int currentColumn;

    public Lexer(String command) {
        this.command = command;
        this.position = 0;
        this.currentChar = command.charAt(position);

        this.currentLine = 1;
        this.currentColumn = 1;
    }

    public Token getNextToken() {

        while (currentChar != END_OF_COMMAND) {

            if (currentChar == COMMENT_CHARACTER) {
                advancePointer();
                skipComment();
            }

            else if (Character.isWhitespace(currentChar)) {
                skipWhitespaces();
            }

            else if (Character.isDigit(currentChar)) {
                return getNumberToken();
            }

            else if (Character.isLetter(currentChar)) {
                return getIdToken();
            }

            else {
                try {
                    Token token = new Token(TokenType.getTypeByContraction(Character.toString(currentChar)));
                    advancePointer();
                    return token;
                } catch (MissingTokenTypeException exception) {
                    throw new LexerOperationException(currentLine, currentColumn, exception.getMessage());
                }
            }
        }

        return new Token(TokenType.EOF);
    }

    public TokenType peekNextToken() {

        while (currentChar != END_OF_COMMAND) {

            if (currentChar == COMMENT_CHARACTER) {
                advancePointer();
                skipComment();
            }

            else if (Character.isWhitespace(currentChar)) {
                skipWhitespaces();
            }

            else if (Character.isDigit(currentChar)) {
                return TokenType.NUMBER_CONST;
            }

            else if (Character.isLetter(currentChar)) {
                return peekIdToken();
            }

            else {
                try {
                    return TokenType.getTypeByContraction(Character.toString(currentChar));
                } catch (MissingTokenTypeException exception) {
                    throw new LexerOperationException(currentLine, currentColumn, exception.getMessage());
                }
            }
        }

        return TokenType.EOF;
    }

    private Token getNumberToken() {

        StringBuilder numberString = new StringBuilder();
        while (currentChar != END_OF_COMMAND && Character.isDigit(currentChar)) {
            numberString.append(currentChar);
            advancePointer();
        }

        if (currentChar == TokenType.DOT.getContraction().charAt(0)) {
            numberString.append(currentChar);
            advancePointer();

            while (currentChar != END_OF_COMMAND && Character.isDigit(currentChar)) {
                numberString.append(currentChar);
                advancePointer();
            }

            return new Token(TokenType.FLOAT_CONST, Float.parseFloat(numberString.toString()));
        }

        return new Token(TokenType.INTEGER_CONST, Integer.parseInt(numberString.toString()));
    }

    private Token getIdToken() {

        StringBuilder name = new StringBuilder();
        while (currentChar != END_OF_COMMAND && Character.isLetterOrDigit(currentChar)) {
            name.append(currentChar);
            advancePointer();
        }

        return RESERVED_KEYWORDS.getOrDefault(name.toString(), new Token(TokenType.ID, name.toString()));
    }

    private TokenType peekIdToken() {

        char peekingChar = currentChar;
        int peekingPosition = position;
        StringBuilder name = new StringBuilder();

        while (peekingChar != END_OF_COMMAND && Character.isLetterOrDigit(peekingChar)) {

            name.append(peekingChar);

            peekingPosition++;

            if (peekingPosition == command.length()) {
                peekingChar = END_OF_COMMAND;
            } else {
                peekingChar = command.charAt(peekingPosition);
            }
        }

        // TODO: refactor without using exceptions

        try {
            return TokenType.getTypeByContraction(name.toString());
        } catch (MissingTokenTypeException exception) {
            return TokenType.ID;
        }
    }

    private void advancePointer() {

        if (currentChar == '\n') {
            currentLine++;
            currentColumn = 0;
        }

        position++;

        if (position == command.length()) {
            currentChar = END_OF_COMMAND;
        } else {
            currentChar = command.charAt(position);
            currentColumn++;
        }
    }

    private void skipWhitespaces() {
        while (currentChar != END_OF_COMMAND && Character.isWhitespace(currentChar))
            advancePointer();
    }

    private void skipComment() {
        while (currentChar != END_OF_COMMAND && currentChar != COMMENT_CHARACTER)
            advancePointer();
        advancePointer();
    }

//    public char peekCharacter() {
//        int peekPosition = position + 1;
//        if (peekPosition == command.length()) return END_OF_COMMAND;
//        else return command.charAt(peekPosition);
//    }

    public char getCurrentChar() {
        return currentChar;
    }
}
