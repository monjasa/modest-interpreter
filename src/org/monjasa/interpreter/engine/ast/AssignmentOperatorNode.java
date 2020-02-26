package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.tokens.Token;
import org.monjasa.interpreter.engine.tokens.TokenType;

import java.util.Locale;

public class AssignmentOperatorNode extends AbstractNode {

    private VariableNode variableNode;
    private Token assignmentToken;
    private AbstractNode operand;

    public AssignmentOperatorNode(VariableNode variableNode, AbstractNode operand) {
        this.variableNode = variableNode;
        this.assignmentToken = new Token(TokenType.ASSIGNMENT, ":=");
        this.operand = operand;
    }

    public VariableNode getVariableNode() {
        return variableNode;
    }

    public AbstractNode getOperand() {
        return operand;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s: %s := %s", super.toString(), variableNode.toString(), operand.toString());
    }
}
