package org.monjasa.interpreter.engine.callstack;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringJoiner;

public class CallStack {

    private Deque<ActivationRecord> records;

    public CallStack() {
        records = new LinkedList<>();
    }

    public void pushRecord(ActivationRecord record) {
        records.push(record);
    }

    public ActivationRecord popRecord() {
        return records.pop();
    }

    public ActivationRecord peekRecord() {
        return records.peek();
    }

    @Override
    public String toString() {

        StringJoiner joiner = new StringJoiner("\n");
        joiner.add("\n|--- CALL STACK ---|");

        Iterator iterator = records.descendingIterator();
        while (iterator.hasNext()) {
            joiner.add(iterator.next().toString());
        }

        return joiner.toString();
    }
}
