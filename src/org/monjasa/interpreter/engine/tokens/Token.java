package org.monjasa.interpreter.engine.tokens;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

public class Token {

    private static ArrayList<TokenType> expressionTokens;
    private static ArrayList<TokenType> termTokens;

    static {
        expressionTokens = new ArrayList<>();
        expressionTokens.add(TokenType.ADDITION);
        expressionTokens.add(TokenType.SUBTRACTION);

        termTokens = new ArrayList<>();
        termTokens.add(TokenType.MULTIPLICATION);
        termTokens.add(TokenType.DIVISION);
    }

    public static boolean isExpressionToken(TokenType tokenType) {
        return expressionTokens.stream().anyMatch(type -> tokenType == type);
    }

    public static boolean isTermToken(TokenType tokenType) {
        return termTokens.stream().anyMatch(type -> tokenType == type);
    }

    private TokenType type;
    private Object value;

    public Token(TokenType type) {
        this.type = type;
        this.value = Optional.empty();
    }

    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public <T> Optional<T> getValue(Class<T> expectedType) {
        if (expectedType.isInstance(value))
            return Optional.ofNullable(expectedType.cast(value));
        else
            // TODO throw exception providing type checking has failed
            return Optional.empty();
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Token: type=\"%s\"; value=\"%s\".",
                getType().toString(), getValue(Object.class));
    }
}
