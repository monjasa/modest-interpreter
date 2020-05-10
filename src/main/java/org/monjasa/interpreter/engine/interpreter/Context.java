package org.monjasa.interpreter.engine.interpreter;

import org.monjasa.interpreter.engine.callstack.CallStack;

public class Context {

    private CallStack callStack;

    public Context() {
        callStack = new CallStack();
    }

    public CallStack getCallStack() {
        return callStack;
    }
}
