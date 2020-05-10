package org.monjasa.interpreter.engine.callstack;

public enum ActivationRecordType {

    PROGRAM("program"),
    PROCEDURE("procedure");

    private String typeName;

    ActivationRecordType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
