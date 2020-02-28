package org.monjasa.interpreter.engine.tokens;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum TokenType {

    BEGIN('{'),
    END('}'),
    ASSIGNMENT('='),

    SEMICOLON(';'),
    DOT('.'),

    INTEGER('d'),
    ID('i'),

    ADDITION('+'),
    SUBTRACTION('-'),
    MULTIPLICATION('*'),
    DIVISION('/'),

    LEFT_PARENTHESIS('('),
    RIGHT_PARENTHESIS(')'),

    EOF((char) 0);


    private static final Map<Character, TokenType> TOKEN_CONTRACTIONS;

    static {
        TOKEN_CONTRACTIONS = Collections.unmodifiableMap(Arrays.stream(values())
                .collect(Collectors.toMap(TokenType::getContraction, tokenType -> tokenType)));
    }

    public static TokenType getTypeByContraction(char contraction) {
        // TODO throw exception providing there was no such contraction
        return TOKEN_CONTRACTIONS.getOrDefault(contraction, EOF);
    }


    private char contraction;

    TokenType(char contraction) {
        this.contraction = contraction;
    }

    public char getContraction() {
        return contraction;
    }
}
