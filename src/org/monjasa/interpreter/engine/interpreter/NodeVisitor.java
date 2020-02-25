package org.monjasa.interpreter.engine.interpreter;

import org.monjasa.interpreter.engine.ast.AbstractNode;
import org.monjasa.interpreter.engine.ast.BinaryOperatorNode;
import org.monjasa.interpreter.engine.ast.NumberOperandNode;
import org.monjasa.interpreter.engine.ast.UnaryOperatorNode;

public abstract class NodeVisitor {

    public abstract String visitUnaryOperatorNode(UnaryOperatorNode node);
    public abstract String visitBinaryOperatorNode(BinaryOperatorNode node);
    public abstract String visitNumberOperandNode(NumberOperandNode node);

    public String genericVisit(AbstractNode node) {
        System.out.println("No such method!");
        return null;
    }

    public String visitNode(AbstractNode node) {
        if (node instanceof UnaryOperatorNode) return visitUnaryOperatorNode((UnaryOperatorNode) node);
        else if (node instanceof BinaryOperatorNode) return visitBinaryOperatorNode((BinaryOperatorNode) node);
        else if (node instanceof NumberOperandNode) return visitNumberOperandNode((NumberOperandNode) node);
        else return genericVisit(node);
    }

}
