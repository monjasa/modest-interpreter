package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.SymbolTable;

import java.util.Optional;

public interface AbstractNode {
    void analyzeNodeSemantic(SymbolTable symbolTable);
    Optional<?> interpretNode(Context context);
}
