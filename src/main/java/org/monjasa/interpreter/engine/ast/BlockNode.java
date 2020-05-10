package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlockNode extends NonTerminalNode {

    private List<DeclarationNode> declarationNodes;
    private CompoundStatementNode compoundStatementNode;

    public BlockNode(List<DeclarationNode> declarationNodeReferences, CompoundStatementNode compoundStatementNode) {
        this.declarationNodes = new ArrayList<>(declarationNodeReferences);
        this.compoundStatementNode = compoundStatementNode;
    }

    @Override
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {
        declarationNodes.forEach(node -> node.analyzeNodeSemantic(currentScope));
        compoundStatementNode.analyzeNodeSemantic(currentScope);
    }

    @Override
    public Optional<?> interpretNode(Context context) {
        declarationNodes.forEach(node -> node.interpretNode(context));
        compoundStatementNode.interpretNode(context);
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "BlockNode{}";
    }
}
