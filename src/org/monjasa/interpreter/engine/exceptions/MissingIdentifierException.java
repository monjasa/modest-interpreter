package org.monjasa.interpreter.engine.exceptions;

public class MissingIdentifierException extends RuntimeException {

    private String identifierName;

    public MissingIdentifierException(String identifierName) {
        this.identifierName = identifierName;
    }

    @Override
    public String toString() {
        return "MissingIdentifierException{" +
                "identifierName='" + identifierName + '\'' +
                "}";
    }

    public String getIdentifierName() {
        return identifierName;
    }
}
