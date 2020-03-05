package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.exceptions.MissingValueException;
import org.monjasa.interpreter.engine.exceptions.UndefinedVariableException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;
import org.monjasa.interpreter.engine.tokens.Token;

import java.util.Optional;

public class VariableNode extends TerminalNode {

    private Token variableToken;

    public VariableNode(Token variableToken) {
        this.variableToken = variableToken;
    }

    @Override
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {

        // TODO : replace try-catch within the method
        String variableName = variableToken.getValue(String.class)
                .orElseThrow(MissingValueException::new);

        currentScope.fetchSymbol(variableName);
    }

    @Override
    public Optional<?> interpretNode(Context context) {

        String variableName = variableToken.getValue(String.class)
                .orElseThrow(RuntimeException::new);

        return Optional.ofNullable(context.getGlobalScope().get(variableName))
                .orElseThrow(UndefinedVariableException::new);
    }

    public Token getVariableToken() {
        return variableToken;
    }
}
