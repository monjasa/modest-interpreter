package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.tokens.Token;

public class BinaryOperatorNode extends AbstractNode {

    private AbstractNode leftNode;
    private Token operatorToken;
    private AbstractNode rightNode;

    public BinaryOperatorNode(AbstractNode leftNode, Token operatorToken, AbstractNode rightNode) {
        this.leftNode = leftNode;
        this.operatorToken = operatorToken;
        this.rightNode = rightNode;
    }

    public AbstractNode getLeftNode() {
        return leftNode;
    }

    public Token getOperatorToken() {
        return operatorToken;
    }

    public AbstractNode getRightNode() {
        return rightNode;
    }
}
