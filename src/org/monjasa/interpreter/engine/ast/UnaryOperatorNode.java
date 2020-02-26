package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.tokens.Token;

public class UnaryOperatorNode extends AbstractNode {

    private Token operatorToken;
    private AbstractNode operandNode;

    public UnaryOperatorNode(Token operatorToken, AbstractNode operandNode) {
        this.operatorToken = operatorToken;
        this.operandNode = operandNode;
    }

    public Token getOperatorToken() {
        return operatorToken;
    }

    public AbstractNode getOperandNode() {
        return operandNode;
    }
}
