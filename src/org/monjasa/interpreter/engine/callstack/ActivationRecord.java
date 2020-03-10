package org.monjasa.interpreter.engine.callstack;

import org.monjasa.interpreter.engine.exceptions.MissingIdentifierException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ActivationRecord {

    private String name;
    private ActivationRecordType type;
    private int nestingLevel;
    private Map<String, Optional<?>> members;

    public ActivationRecord(String name, ActivationRecordType type, int nestingLevel) {
        this.name = name;
        this.type = type;
        this.nestingLevel = nestingLevel;
        this.members = new HashMap<>();
    }

    public void putMember(String identifier, Optional<?> value) {
        members.put(identifier, value);
    }

    public Optional<?> getMember(String identifier) throws MissingIdentifierException {

        if (members.containsKey(identifier))
            return members.get(identifier);
        else
            throw new MissingIdentifierException(identifier);
    }

    @Override
    public String toString() {
        return "ActivationRecord{" +
                "name='" + name + '\'' +
                ", type=" + type.getTypeName() +
                ", nestingLevel=" + nestingLevel +
                ", members=" + members +
                '}';
    }
}
