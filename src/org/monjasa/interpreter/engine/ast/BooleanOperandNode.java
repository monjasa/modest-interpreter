package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;
import org.monjasa.interpreter.engine.tokens.Token;

import java.util.Optional;

public class BooleanOperandNode extends TerminalNode {

    private Token booleanToken;

    public BooleanOperandNode(Token booleanToken) {
        this.booleanToken = booleanToken;
    }

    @Override
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {

        // TODO: implement methods

    }

    @Override
    public Optional<?> interpretNode(Context context) {
        return booleanToken.getValue(Boolean.class);
    }
}
