package org.monjasa.interpreter.engine.exceptions;

public class WrongParametersNumberException extends RuntimeException {

    private int expectedNumber;
    private int actualNumber;

    public WrongParametersNumberException(int expectedNumber, int actualNumber) {
        this.expectedNumber = expectedNumber;
        this.actualNumber = actualNumber;
    }

    @Override
    public String toString() {
        return "WrongParametersNumberException{" +
                "expectedNumber=" + expectedNumber +
                ", actualNumber=" + actualNumber +
                '}';
    }
}
