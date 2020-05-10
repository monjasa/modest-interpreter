package org.monjasa.interpreter.engine.exceptions;

import java.util.Locale;

public class MissingTokenTypeException extends RuntimeException {

    private String tokenContraction;

    public MissingTokenTypeException(String tokenContraction) {
        this.tokenContraction = tokenContraction;
    }

    @Override
    public String getMessage() {
        return String.format(Locale.US, "No such token's contraction: '%s'", tokenContraction);
    }

    @Override
    public String toString() {
        return "MissingTokenTypeException{" +
                "tokenContraction=" + tokenContraction +
                '}';
    }
}
