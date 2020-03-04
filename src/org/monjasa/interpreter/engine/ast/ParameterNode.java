package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.SymbolTable;

import java.util.Optional;

public class ParameterNode extends NonTerminalNode {

    private VariableNode variableNode;
    private OperandTypeNode variableType;

    public ParameterNode(VariableNode variableNode, OperandTypeNode variableType) {
        this.variableNode = variableNode;
        this.variableType = variableType;
    }

    @Override
    public void analyzeNodeSemantic(SymbolTable symbolTable) {
        // TODO: implement method
    }

    @Override
    public Optional<?> interpretNode(Context context) {
        return Optional.empty();
    }
}
