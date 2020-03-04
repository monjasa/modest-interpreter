package org.monjasa.interpreter.engine.exceptions;

public class IdentifierDuplicationException extends RuntimeException {

    private String identifierName;

    public IdentifierDuplicationException(String identifierName) {
        this.identifierName = identifierName;
    }

    @Override
    public String toString() {
        return "IdentifierDuplicationException{" +
                "identifierName='" + identifierName + '\'' +
                '}';
    }

    public String getIdentifierName() {
        return identifierName;
    }
}
