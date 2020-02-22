package org.monjasa.interpreter.engine.tokens;

import java.util.ArrayList;

public class Token {

    private static ArrayList<TokenType> interpretTokens;
    private static ArrayList<TokenType> termTokens;

    static {
        interpretTokens = new ArrayList<>();
        interpretTokens.add(TokenType.ADDITION);
        interpretTokens.add(TokenType.SUBTRACTION);

        termTokens = new ArrayList<>();
        termTokens.add(TokenType.MULTIPLICATION);
        termTokens.add(TokenType.DIVISION);
    }

    private TokenType type;
    private String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public static boolean isInterpretToken(TokenType tokenType) {
        return interpretTokens.stream().anyMatch(type -> tokenType == type);
    }

    public static boolean isTermToken(TokenType tokenType) {
        return termTokens.stream().anyMatch(type -> tokenType == type);
    }
}
