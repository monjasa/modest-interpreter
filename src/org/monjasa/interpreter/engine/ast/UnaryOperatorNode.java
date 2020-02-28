package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.exceptions.InappropriateOperatorException;
import org.monjasa.interpreter.engine.exceptions.MissingValueException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.tokens.Token;

import java.util.Optional;

public class UnaryOperatorNode extends NonTerminalNode {

    private Token operatorToken;
    private AbstractNode operandNode;

    public UnaryOperatorNode(Token operatorToken, AbstractNode operandNode) {
        this.operatorToken = operatorToken;
        this.operandNode = operandNode;
    }

    @Override
    public Optional<?> interpretNode(Context context) {

        // TODO : replace Integer with wild class a.k.a. ? extends Number

        int operandValue = ((Optional<Integer>) operandNode.interpretNode(context))
                .orElseThrow(MissingValueException::new);

        switch (operatorToken.getType()) {
            case ADDITION:
                return Optional.of(operandValue);
            case SUBTRACTION:
                return Optional.of(-1 * operandValue);
            default:
                throw new InappropriateOperatorException();
        }
    }
}
