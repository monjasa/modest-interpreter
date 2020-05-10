package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;
import org.monjasa.interpreter.engine.tokens.Token;

import java.util.Optional;

public class OperandTypeNode extends TerminalNode {

    private Token typeToken;

    public OperandTypeNode(Token typeToken) {
        this.typeToken = typeToken;
    }

    @Override
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {

    }

    @Override
    public Optional<?> interpretNode(Context context) {
        return Optional.empty();
    }

    public Token getTypeToken() {
        return typeToken;
    }

    @Override
    public String toString() {
        return "OperandTypeNode{" +
                "typeToken=" + typeToken +
                '}';
    }
}
