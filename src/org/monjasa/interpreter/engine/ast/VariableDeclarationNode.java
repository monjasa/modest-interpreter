package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.exceptions.IdentifierDuplicationException;
import org.monjasa.interpreter.engine.exceptions.MissingValueException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.SymbolTable;
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
    public void analyzeNodeSemantic(SymbolTable symbolTable) {

        String variableName = variableNode.getVariableToken().getValue(String.class)
                .orElseThrow(MissingValueException::new);
        TokenType variableType = typeNode.getTypeToken().getType();

        if (symbolTable.containsSymbol(variableName))
            throw new IdentifierDuplicationException(variableName);

        Symbol variableTypeSymbol = symbolTable.fetchSymbol(variableType.name());
        symbolTable.defineSymbol(new VariableSymbol(variableName, variableTypeSymbol));
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
