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
    public Optional<?> interpretNode(Context context) {

        // TODO : replace Integer with wild class a.k.a. ? extends Number

        int leftValue = ((Optional<Integer>) leftNode.interpretNode(context))
                .orElseThrow(MissingValueException::new);
        int rightValue = ((Optional<Integer>) rightNode.interpretNode(context))
                .orElseThrow(MissingValueException::new);

        switch (operatorToken.getType()) {
            case ADDITION:
                return Optional.of(leftValue + rightValue);
            case SUBTRACTION:
                return Optional.of(leftValue - rightValue);
            case MULTIPLICATION:
                return Optional.of(leftValue * rightValue);
            case DIVISION:
                return Optional.of(leftValue / rightValue);
            default:
                throw new InappropriateOperatorException();
        }
    }
}
