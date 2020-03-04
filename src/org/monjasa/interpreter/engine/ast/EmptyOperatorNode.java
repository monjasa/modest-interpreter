package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.SymbolTable;

import java.util.Optional;

public class EmptyOperatorNode extends TerminalNode {

    @Override
    public void analyzeNodeSemantic(SymbolTable symbolTable) {

    }

    @Override
    public Optional<?> interpretNode(Context context) {
        return Optional.empty();
    }
}
