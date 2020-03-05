package org.monjasa.interpreter.engine.semanticanalyzer;

import org.monjasa.interpreter.engine.ast.AbstractNode;

public class SemanticAnalyzer {

    private ScopedSymbolTable scopedSymbolTable;

    public SemanticAnalyzer() {
        scopedSymbolTable = new ScopedSymbolTable("global", 1);
    }

    public void analyzeSemantic(AbstractNode syntaxTreeRoot) {
        syntaxTreeRoot.analyzeNodeSemantic(scopedSymbolTable);
    }

    public ScopedSymbolTable getScopedSymbolTable() {
        return scopedSymbolTable;
    }
}
