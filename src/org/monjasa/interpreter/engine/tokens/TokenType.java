package org.monjasa.interpreter.engine.tokens;

import org.monjasa.interpreter.engine.exceptions.MissingTokenTypeException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum TokenType {

    PROGRAM('!'),
    PROCEDURE('p'),
    VARIABLE_DECLARATION_BLOCK('$'),
    EOF('\0'),

    BEGIN('{'),
    END('}'),
    ASSIGNMENT('='),

    COLON(':'),
    SEMICOLON(';'),
    COMMA(','),
    DOT('.'),

    INTEGER_TYPE('d'),
    FLOAT_TYPE('f'),
    INTEGER_CONST('1'),
    FLOAT_CONST('2'),

    ID('i'),

    ADDITION('+'),
    SUBTRACTION('-'),
    MULTIPLICATION('*'),
    FLOAT_DIVISION('/'),

    LEFT_PARENTHESIS('('),
    RIGHT_PARENTHESIS(')');


    private static final Map<Character, TokenType> TOKEN_CONTRACTIONS;

    static {
        TOKEN_CONTRACTIONS = Collections.unmodifiableMap(Arrays.stream(values())
                .collect(Collectors.toMap(TokenType::getContraction, tokenType -> tokenType)));
    }

    public static TokenType getTypeByContraction(char contraction) throws MissingTokenTypeException {

        if (TOKEN_CONTRACTIONS.containsKey(contraction))
            return TOKEN_CONTRACTIONS.get(contraction);
        else
            throw new MissingTokenTypeException(contraction);
    }

    public static Optional<?> getDefaultValue(TokenType type) {

        switch (type) {
            case INTEGER_TYPE:
                return Optional.of(0);
            case FLOAT_TYPE:
                return Optional.of(0.0f);
            default:
                throw new RuntimeException();
        }
    }

    private char contraction;

    TokenType(char contraction) {
        this.contraction = contraction;
    }

    public char getContraction() {
        return contraction;
    }
}
