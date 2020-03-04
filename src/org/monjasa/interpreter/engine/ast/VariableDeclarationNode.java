package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.exceptions.MissingValueException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.symbols.BuiltinTypeSymbol;
import org.monjasa.interpreter.engine.symbols.Symbol;
import org.monjasa.interpreter.engine.symbols.VariableSymbol;
import org.monjasa.interpreter.engine.tokens.TokenType;

import java.util.Optional;

public class VariableDeclarationNode extends DeclarationNode {

    private VariableNode variableNode;
    private OperandTypeNode typeNode;

    public VariableDeclarationNode(VariableNode variableNode, OperandTypeNode typeNode) {
        this.variableNode = variableNode;
        this.typeNode = typeNode;
    }

    @Override
    public void analyzeNodeSemantic(Context context) {

        String variableName = variableNode.getVariableToken().getValue(String.class)
                .orElseThrow(MissingValueException::new);
        TokenType variableType = typeNode.getTypeToken().getType();

        Symbol variableTypeSymbol = context.getSymbolTable().fetchSymbol(variableType.name());
        context.getSymbolTable().defineSymbol(new VariableSymbol(variableName, variableTypeSymbol));
    }

    @Override
    public Optional<?> interpretNode(Context context) {

        String variableName = variableNode.getVariableToken().getValue(String.class)
                .orElseThrow(MissingValueException::new);
        TokenType variableType = typeNode.getTypeToken().getType();

        context.getGlobalScope().put(variableName, TokenType.getDefaultValue(variableType));

        return Optional.empty();
    }
}
