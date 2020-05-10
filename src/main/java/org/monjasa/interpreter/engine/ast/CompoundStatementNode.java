package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompoundStatementNode extends NonTerminalNode {

    private List<AbstractNode> childNodes;

    public CompoundStatementNode(List<AbstractNode> nodeReferences) {
        childNodes = new ArrayList<>(nodeReferences);
    }

    @Override
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {
        childNodes.forEach(child -> child.analyzeNodeSemantic(currentScope));
    }

    @Override
    public Optional<?> interpretNode(Context context) {
        childNodes.forEach(child -> child.interpretNode(context));
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "CompoundStatementNode{}";
    }
}
