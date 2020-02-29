package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.exceptions.MissingValueException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.tokens.TokenType;

import java.util.Optional;

public class VariableDeclarationNode extends NonTerminalNode {

    private VariableNode variableNode;
    private OperandTypeNode typeNode;

    public VariableDeclarationNode(VariableNode variableNode, OperandTypeNode typeNode) {
        this.variableNode = variableNode;
        this.typeNode = typeNode;
    }

    @Override
    public Optional<?> interpretNode(Context context) {

        context.getGlobalScope().put(variableNode.getVariableToken().getValue(String.class)
                .orElseThrow(MissingValueException::new), TokenType.getDefaultValue(typeNode.getTypeToken().getType()));

        return Optional.empty();
    }
}
