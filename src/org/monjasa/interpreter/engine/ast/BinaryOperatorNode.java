package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.exceptions.InappropriateOperatorException;
import org.monjasa.interpreter.engine.exceptions.MissingValueException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.tokens.Token;

import java.util.Optional;

public class BinaryOperatorNode extends NonTerminalNode {

    private AbstractNode leftNode;
    private Token operatorToken;
    private AbstractNode rightNode;

    public BinaryOperatorNode(AbstractNode leftNode, Token operatorToken, AbstractNode rightNode) {
        this.leftNode = leftNode;
        this.operatorToken = operatorToken;
        this.rightNode = rightNode;
    }

    @Override
    public void analyzeNodeSemantic(Context context) {
        leftNode.analyzeNodeSemantic(context);
        rightNode.analyzeNodeSemantic(context);
    }

    @Override
    public Optional<?> interpretNode(Context context) {

        Number leftValue = (Number) leftNode.interpretNode(context).orElseThrow(MissingValueException::new);
        Number rightValue = (Number) rightNode.interpretNode(context).orElseThrow(MissingValueException::new);
        Optional<? extends Number> result;

        if (leftValue instanceof Integer && rightValue instanceof Integer) {

            switch (operatorToken.getType()) {
                case ADDITION:
                    result = Optional.of(leftValue.intValue() + rightValue.intValue());
                    break;
                case SUBTRACTION:
                    result = Optional.of(leftValue.intValue() - rightValue.intValue());
                    break;
                case MULTIPLICATION:
                    result = Optional.of(leftValue.intValue() * rightValue.intValue());
                    break;
                case FLOAT_DIVISION:
                    result = Optional.of(leftValue.intValue() / rightValue.intValue());
                    break;
                default:
                    throw new InappropriateOperatorException();
            }

        } else {
            switch (operatorToken.getType()) {
                case ADDITION:
                    result = Optional.of(leftValue.floatValue() + rightValue.floatValue());
                    break;
                case SUBTRACTION:
                    result = Optional.of(leftValue.floatValue() - rightValue.floatValue());
                    break;
                case MULTIPLICATION:
                    result = Optional.of(leftValue.floatValue() * rightValue.floatValue());
                    break;
                case FLOAT_DIVISION:
                    result = Optional.of(leftValue.floatValue() / rightValue.floatValue());
                    break;
                default:
                    throw new InappropriateOperatorException();
            }
        }

        return result;
    }
}
