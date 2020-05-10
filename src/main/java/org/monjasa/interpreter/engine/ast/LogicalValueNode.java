package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;

import java.util.Optional;

public class LogicalValueNode extends TerminalNode {

    private AbstractNode value;

    public LogicalValueNode(AbstractNode value) {
        this.value = value;
    }

    @Override
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {

        // TODO: implement methods

        value.analyzeNodeSemantic(currentScope);
    }

    @Override
    public Optional<?> interpretNode(Context context) {
        return value.interpretNode(context);
    }
}
