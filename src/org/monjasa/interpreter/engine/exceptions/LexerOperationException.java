package org.monjasa.interpreter.engine.exceptions;

public class LexerOperationException extends RuntimeException {

    private int commandLine;
    private int commandColumn;
    private String message;

    public LexerOperationException(int commandLine, int commandColumn, String message) {
        this.commandLine = commandLine;
        this.commandColumn = commandColumn;
        this.message = message;
    }

    @Override
    public String toString() {
        return "LexerOperationException{" +
                "commandLine=" + commandLine +
                ", commandColumn=" + commandColumn +
                ", message='" + message + '\'' +
                '}';
    }
}
