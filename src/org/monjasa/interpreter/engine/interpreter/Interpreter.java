package org.monjasa.interpreter.engine.interpreter;

import org.monjasa.interpreter.engine.ast.*;
import org.monjasa.interpreter.engine.parser.Parser;

import java.util.HashMap;

public class Interpreter extends NodeVisitor {

    private Parser parser;

    private HashMap<String, String> globalScope;

    public Interpreter(Parser parser) {
        this.parser = parser;
        globalScope = new HashMap<>();
    }

    public String interpret() {
        AbstractNode syntaxTreeRoot = parser.parseCommand();
        return visitNode(syntaxTreeRoot);
    }

    @Override
    public String visitUnaryOperatorNode(UnaryOperatorNode node) {

        String result = null;

        switch (node.getOperatorToken().getType()) {
            case ADDITION:
                result = visitNode(node.getOperandNode());
                break;
            case SUBTRACTION:
                result = String.valueOf(-1 * Integer.parseInt(visitNode(node.getOperandNode())));
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

    @Override
    public String visitCompoundStatementNode(CompoundStatementNode node) {

        node.getChildNodes().forEach(this::visitNode);
        return "";
    }

    @Override
    public String visitEmptyOperandNode(EmptyOperatorNode node) {
        return "";
    }

    @Override
    public String visitAssignmentOperatorNode(AssignmentOperatorNode node) {

        String variableName = node.getVariableNode().getVariableToken().getValue();
        String variableValue = visitNode(node.getOperand());
        globalScope.put(variableName, variableValue);
        return "";
    }

    @Override
    public String visitVariableNode(VariableNode node) {

        String variableName = node.getVariableToken().getValue();
        if (globalScope.containsKey(variableName)) return globalScope.get(variableName);
        else throw new RuntimeException();
    }

    public HashMap<String, String> getGlobalScope() {
        return globalScope;
    }
}
