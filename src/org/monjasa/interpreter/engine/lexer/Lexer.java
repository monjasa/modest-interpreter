package org.monjasa.interpreter.engine.lexer;

import org.monjasa.interpreter.engine.tokens.Token;
import org.monjasa.interpreter.engine.tokens.TokenType;

import java.util.*;
import java.util.stream.Collectors;

public class Lexer {

    private static final char END_OF_COMMAND = 0;
    private static final char COMMENT_CHARACTER = '`';

    private static final Map<String, Token> RESERVED_KEYWORDS;

    static {
        HashMap<String, Token> temporaryMap = new HashMap<>();

        ArrayList<Token> tokens = new ArrayList<>();

        tokens.add(new Token(TokenType.PROGRAM, "program"));
        tokens.add(new Token(TokenType.VARIABLE_DECLARATION_BLOCK, "let"));

        tokens.add(new Token(TokenType.INTEGER_TYPE, "int"));
        tokens.add(new Token(TokenType.FLOAT_TYPE, "float"));

        tokens.add(new Token(TokenType.BEGIN, "do"));
        tokens.add(new Token(TokenType.END, "end"));

        RESERVED_KEYWORDS = Collections.unmodifiableMap(tokens.stream()
                .collect(Collectors.toMap(token -> token.getValue(String.class).orElseThrow(RuntimeException::new)
                        , token -> token)));
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

            if (currentChar == COMMENT_CHARACTER) {
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
                Token token = new Token(TokenType.getTypeByContraction(currentChar));
                advancePointer();
                return token;
            }
        }

        return new Token(TokenType.EOF);
    }

    private Token getNumberToken() {

        StringBuilder numberString = new StringBuilder();
        while (currentChar != END_OF_COMMAND && Character.isDigit(currentChar)) {
            numberString.append(currentChar);
            advancePointer();
        }

        if (currentChar == TokenType.DOT.getContraction()) {
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

    private void advancePointer() {
        position++;
        if (position == command.length()) currentChar = END_OF_COMMAND;
        else currentChar = command.charAt(position);
    }

    private void skipWhitespaces() {
        while (currentChar != END_OF_COMMAND && Character.isWhitespace(currentChar))
            advancePointer();
    }

    private void skipComment() {
        while (currentChar != COMMENT_CHARACTER)
            advancePointer();
        advancePointer();
    }

//    public char peekCharacter() {
//        int peekPosition = position + 1;
//        if (peekPosition == command.length()) return END_OF_COMMAND;
//        else return command.charAt(peekPosition);
//    }

}
