package org.monjasa.interpreter.engine.interpreter;

import org.monjasa.interpreter.engine.ast.*;
import org.monjasa.interpreter.engine.parser.Parser;

import java.util.Optional;

public class Interpreter {

    private Parser parser;
    private Context context;

    public Interpreter(Parser parser) {
        this.parser = parser;
        this.context = new Context();
    }

    public Optional<?> interpret() {
        AbstractNode syntaxTreeRoot = parser.parseCommand();
        return syntaxTreeRoot.interpretNode(context);
    }

    public Context getContext() {
        return context;
    }
}
