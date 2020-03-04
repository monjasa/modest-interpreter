package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;

import java.util.ArrayList;
import java.util.Optional;

public class BlockNode extends NonTerminalNode {

    private ArrayList<DeclarationNode> declarationNodes;
    private CompoundStatementNode compoundStatementNode;

    public BlockNode(ArrayList<DeclarationNode> declarationNodeReferences, CompoundStatementNode compoundStatementNode) {
        this.declarationNodes = new ArrayList<>(declarationNodeReferences);
        this.compoundStatementNode = compoundStatementNode;
    }

    @Override
    public void analyzeNodeSemantic(Context context) {
        declarationNodes.forEach(node -> node.analyzeNodeSemantic(context));
        compoundStatementNode.analyzeNodeSemantic(context);
    }

    @Override
    public Optional<?> interpretNode(Context context) {

        declarationNodes.forEach(node -> node.interpretNode(context));
        compoundStatementNode.interpretNode(context);
        return Optional.empty();
    }
}
