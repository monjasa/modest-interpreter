package org.monjasa.interpreter.engine.interpreter;

import org.monjasa.interpreter.engine.ast.AbstractNode;
import org.monjasa.interpreter.engine.ast.BinaryOperatorNode;
import org.monjasa.interpreter.engine.ast.NumberOperandNode;
import org.monjasa.interpreter.engine.ast.UnaryOperatorNode;
import org.monjasa.interpreter.engine.parser.Parser;

public class Interpreter extends NodeVisitor {

    private Parser parser;

    public Interpreter(Parser parser) {
        this.parser = parser;
    }

    @Override
    public String visitUnaryOperatorNode(UnaryOperatorNode node) {

        String result = null;

        switch (node.getOperatorToken().getType()) {
            case ADDITION:
                result = visitNode(node.getNode());
                break;
            case SUBTRACTION:
                result = String.valueOf(-1 * Integer.parseInt(visitNode(node.getNode())));
                break;
        }

        return result;
    }

    @Override
    public String visitBinaryOperatorNode(BinaryOperatorNode node) {

        String result = null;

        String leftValue = visitNode(node.getLeftNode());
        String rightValue = visitNode(node.getRightNode());

        switch (node.getOperatorToken().getType()) {
            case ADDITION:
                result = String.valueOf(Integer.parseInt(leftValue) + Integer.parseInt(rightValue));
                break;
            case SUBTRACTION:
                result = String.valueOf(Integer.parseInt(leftValue) - Integer.parseInt(rightValue));
                break;
            case MULTIPLICATION:
                result = String.valueOf(Integer.parseInt(leftValue) * Integer.parseInt(rightValue));
                break;
            case DIVISION:
                result = String.valueOf(Integer.parseInt(leftValue) / Integer.parseInt(rightValue));
                break;
        }

        return result;
    }

    @Override
    public String visitNumberOperandNode(NumberOperandNode node) {
        return node.getNumberToken().getValue();
    }

    public String interpret() {
        AbstractNode syntaxTreeRoot = parser.parseCommand();
        return visitNode(syntaxTreeRoot);
    }

}
