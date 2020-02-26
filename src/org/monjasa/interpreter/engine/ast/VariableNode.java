package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.tokens.Token;

import java.util.Locale;

public class VariableNode extends AbstractNode {

    private Token variableToken;

    public VariableNode(Token variableToken) {
        this.variableToken = variableToken;
    }

    public Token getVariableToken() {
        return variableToken;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s: [%s]", super.toString(), variableToken.getValue());
    }
}
