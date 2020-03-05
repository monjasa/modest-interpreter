package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.exceptions.InappropriateOperatorException;
import org.monjasa.interpreter.engine.exceptions.MissingValueException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;
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
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {
        operandNode.analyzeNodeSemantic(currentScope);
    }

    @Override

    public Optional<?> interpretNode(Context context) {

        // TODO: refactor with wild classes a.k.a. ?

        Number operandValue = (Number) operandNode.interpretNode(context).orElseThrow(MissingValueException::new);
        Number result;

        switch (operatorToken.getType()) {
            case ADDITION:
                result = operandValue.floatValue();
                break;
            case SUBTRACTION:
                result = -1 * operandValue.floatValue();
                break;
            default:
                throw new InappropriateOperatorException();
        }

        if (operandValue instanceof Integer)
            return Optional.of(result.intValue());
        else
            return Optional.of(result.floatValue());
    }
}
