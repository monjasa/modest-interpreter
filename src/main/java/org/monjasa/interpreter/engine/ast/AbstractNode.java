package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;

import java.util.Optional;

public interface AbstractNode {
    void analyzeNodeSemantic(ScopedSymbolTable currentScope);
    Optional<?> interpretNode(Context context);
}
