package org.monjasa.interpreter.engine.callstack;

public enum ActivationRecordType {

    PROGRAM("program");

    private String typeName;

    ActivationRecordType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
