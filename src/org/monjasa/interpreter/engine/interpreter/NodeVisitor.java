package org.monjasa.interpreter.engine.interpreter;

import org.monjasa.interpreter.engine.ast.*;

public abstract class NodeVisitor {

    public abstract String visitUnaryOperatorNode(UnaryOperatorNode node);
    public abstract String visitBinaryOperatorNode(BinaryOperatorNode node);
    public abstract String visitNumberOperandNode(NumberOperandNode node);
    public abstract String visitCompoundStatementNode(CompoundStatementNode node);
    public abstract String visitEmptyOperandNode(EmptyOperatorNode node);
    public abstract String visitAssignmentOperatorNode(AssignmentOperatorNode node);
    public abstract String visitVariableNode(VariableNode node);

    public String genericVisit(AbstractNode node) {
        System.out.println("No such method!");
        return null;
    }

    public String visitNode(AbstractNode node) {
        if (node instanceof UnaryOperatorNode) return visitUnaryOperatorNode((UnaryOperatorNode) node);
        else if (node instanceof BinaryOperatorNode) return visitBinaryOperatorNode((BinaryOperatorNode) node);
        else if (node instanceof NumberOperandNode) return visitNumberOperandNode((NumberOperandNode) node);
        else if (node instanceof CompoundStatementNode) return visitCompoundStatementNode((CompoundStatementNode) node);
        else if (node instanceof EmptyOperatorNode) return visitEmptyOperandNode((EmptyOperatorNode) node);
        else if (node instanceof AssignmentOperatorNode) return visitAssignmentOperatorNode((AssignmentOperatorNode) node);
        else if (node instanceof VariableNode) return visitVariableNode((VariableNode) node);
        else return genericVisit(node);
    }

}
