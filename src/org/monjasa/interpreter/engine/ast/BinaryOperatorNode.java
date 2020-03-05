package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.exceptions.InappropriateOperatorException;
import org.monjasa.interpreter.engine.exceptions.MissingValueException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;
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
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {
        leftNode.analyzeNodeSemantic(currentScope);
        rightNode.analyzeNodeSemantic(currentScope);
    }

    @Override
    public Optional<?> interpretNode(Context context) {

        // TODO: refactor with wild classes a.k.a. ?

        Number leftValue = (Number) leftNode.interpretNode(context).orElseThrow(MissingValueException::new);
        Number rightValue = (Number) rightNode.interpretNode(context).orElseThrow(MissingValueException::new);
        Number result;

        if (leftValue instanceof Integer && rightValue instanceof Integer) {

            switch (operatorToken.getType()) {
                case ADDITION:
                    result = leftValue.intValue() + rightValue.intValue();
                    break;
                case SUBTRACTION:
                    result = leftValue.intValue() - rightValue.intValue();
                    break;
                case MULTIPLICATION:
                    result = leftValue.intValue() * rightValue.intValue();
                    break;
                case FLOAT_DIVISION:
                    result = leftValue.intValue() / rightValue.intValue();
                    break;
                default:
                    throw new InappropriateOperatorException();
            }

        } else {

            switch (operatorToken.getType()) {
                case ADDITION:
                    result = leftValue.floatValue() + rightValue.floatValue();
                    break;
                case SUBTRACTION:
                    result = leftValue.floatValue() - rightValue.floatValue();
                    break;
                case MULTIPLICATION:
                    result = leftValue.floatValue() * rightValue.floatValue();
                    break;
                case FLOAT_DIVISION:
                    result = leftValue.floatValue() / rightValue.floatValue();
                    break;
                default:
                    throw new InappropriateOperatorException();
            }
        }

        return Optional.of(result);
    }
}
