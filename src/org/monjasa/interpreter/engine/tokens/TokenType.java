package org.monjasa.interpreter.engine.tokens;

import org.monjasa.interpreter.engine.exceptions.MissingTokenTypeException;

import java.util.*;
import java.util.stream.Collectors;

public enum TokenType {

    PROGRAM("program", true),
    VARIABLE_DECLARATION_BLOCK("let", true),
    PROCEDURE("procedure", true),
    EOF('\0'),

    BEGIN('{'),
    END('}'),
    IF("if", true),
    ELSE("else", true),
    ASSIGNMENT('='),

    COLON(':'),
    SEMICOLON(';'),
    COMMA(','),
    DOT('.'),

    INTEGER_TYPE("int", true),
    FLOAT_TYPE("float", true),
    BOOLEAN_TYPE("boolean", true),
    INTEGER_CONST("0"),
    FLOAT_CONST("0.0"),
    TRUE_CONST("true", true),
    FALSE_CONST("false", true),

    ID("id"),

    ADDITION('+'),
    SUBTRACTION('-'),
    MULTIPLICATION('*'),
    DIVISION('/'),

    LEFT_PARENTHESIS('('),
    RIGHT_PARENTHESIS(')');


    private static final Map<String, TokenType> TOKEN_CONTRACTIONS;
    private static final List<TokenType> KEYWORDS;

    static {
        TOKEN_CONTRACTIONS = Collections.unmodifiableMap(Arrays.stream(values())
                .collect(Collectors.toMap(TokenType::getContraction, tokenType -> tokenType)));

        KEYWORDS = Collections.unmodifiableList(Arrays.stream(values())
                .filter(tokenType -> tokenType.isKeyword)
                .collect(Collectors.toList()));
    }

    public static TokenType getTypeByContraction(String contraction) throws MissingTokenTypeException {

        if (TOKEN_CONTRACTIONS.containsKey(contraction))
            return TOKEN_CONTRACTIONS.get(contraction);
        else
            throw new MissingTokenTypeException(contraction);
    }

    public static List<TokenType> getKeywords() {
        return KEYWORDS;
    }

    public static Optional<?> getDefaultValue(TokenType type) {

        switch (type) {
            case INTEGER_TYPE:
                return Optional.of(0);
            case FLOAT_TYPE:
                return Optional.of(0.0f);
            case BOOLEAN_TYPE:
                return Optional.of(false);
            default:
                throw new RuntimeException();
        }
    }

    private String contraction;
    private boolean isKeyword;

    TokenType(String contraction) {
        this.contraction = contraction;
        this.isKeyword = false;
    }

    TokenType(char contraction) {
        this.contraction = Character.toString(contraction);
        this.isKeyword = false;
    }

    TokenType(String contraction, boolean isKeyword) {
        this.contraction = contraction;
        this.isKeyword = isKeyword;
    }

    public String getContraction() {
        return contraction;
    }
}
