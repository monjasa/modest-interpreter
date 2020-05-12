package org.monjasa.interpreter.engine.interpreter;

import org.monjasa.interpreter.Client;
import org.monjasa.interpreter.engine.callstack.CallStack;

public class Context {

    private Client client;
    private CallStack callStack;

    public Context(Client client) {
        this.client = client;
        this.callStack = new CallStack();
    }

    public Client getClient() {
        return client;
    }

    public CallStack getCallStack() {
        return callStack;
    }
}
