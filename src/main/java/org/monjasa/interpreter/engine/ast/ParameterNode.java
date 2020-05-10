package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;

import java.util.Optional;

public class ParameterNode extends NonTerminalNode {

    private VariableNode variableNode;
    private OperandTypeNode variableType;

    public ParameterNode(VariableNode variableNode, OperandTypeNode variableType) {
        this.variableNode = variableNode;
        this.variableType = variableType;
    }

    @Override
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {
        // TODO: implement method
    }

    @Override
    public Optional<?> interpretNode(Context context) {
        return Optional.empty();
    }

    public VariableNode getVariableNode() {
        return variableNode;
    }

    public OperandTypeNode getVariableType() {
        return variableType;
    }
}
