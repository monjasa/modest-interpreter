package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.tokens.Token;

public class NumberOperandNode extends AbstractNode {

    private Token numberToken;

    public NumberOperandNode(Token numberToken) {
        this.numberToken = numberToken;
    }

    public Token getNumberToken() {
        return numberToken;
    }

    @Override
    public String toString() {
        return numberToken.getValue();
    }
}
