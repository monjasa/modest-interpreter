package org.monjasa.interpreter.engine.semanticanalyzer;

import org.monjasa.interpreter.engine.ast.AbstractNode;

public class SemanticAnalyzer {

    private SymbolTable symbolTable;

    public SemanticAnalyzer() {
        symbolTable = new SymbolTable();
    }

    public void analyzeSemantic(AbstractNode syntaxTreeRoot) {
        syntaxTreeRoot.analyzeNodeSemantic(symbolTable);
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }
}
