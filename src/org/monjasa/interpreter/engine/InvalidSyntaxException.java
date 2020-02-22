package org.monjasa.interpreter.engine;

public class InvalidSyntaxException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Invalid Syntax Error!";
    }
}
