package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.Client;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Optional;

public class CompoundStatementNode extends NonTerminalNode {

    private ArrayList<AbstractNode> childNodes;

    public CompoundStatementNode(ArrayList<AbstractNode> nodeReferences) {
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
