package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;
import org.monjasa.interpreter.engine.tokens.Token;

import java.util.Optional;

public class StringOperandNode extends TerminalNode {

    private Token stringToken;

    public StringOperandNode(Token stringToken) {
        this.stringToken = stringToken;
    }

    @Override
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {

    }

    @Override
    public Optional<?> interpretNode(Context context) {
        return stringToken.getValue(String.class);
    }
}
