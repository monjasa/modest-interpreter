package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.tokens.Token;

public class UnaryOperatorNode extends AbstractNode {

    private Token operatorToken;
    private AbstractNode node;

    public UnaryOperatorNode(Token operatorToken, AbstractNode node) {
        this.operatorToken = operatorToken;
        this.node = node;
    }

    public Token getOperatorToken() {
        return operatorToken;
    }

    public AbstractNode getNode() {
        return node;
    }
}
