package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.exceptions.MissingValueException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;
import org.monjasa.interpreter.engine.tokens.Token;
import org.monjasa.interpreter.engine.tokens.TokenType;

import java.util.Optional;

public class LogicalTermNode extends NonTerminalNode {

    private AbstractNode leftOperandNode;
    private Token comparingToken;
    private AbstractNode rightOperandNode;

    public LogicalTermNode(AbstractNode leftOperandNode) {
        this.leftOperandNode = leftOperandNode;
        this.comparingToken = new Token(TokenType.EMPTY_TOKEN);
        this.rightOperandNode = new EmptyOperatorNode();
    }

    public LogicalTermNode(AbstractNode leftOperandNode, Token comparingToken, AbstractNode rightOperandNode) {
        this.leftOperandNode = leftOperandNode;
        this.comparingToken = comparingToken;
        this.rightOperandNode = rightOperandNode;
    }

    @Override
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {

        // TODO: implement methods

        leftOperandNode.analyzeNodeSemantic(currentScope);
        rightOperandNode.analyzeNodeSemantic(currentScope);
    }

    @Override
    public Optional<?> interpretNode(Context context) {

        Optional<?> result = Optional.empty();

        if (comparingToken.getType() != TokenType.EMPTY_TOKEN) {

            Number leftValue = (Number) leftOperandNode.interpretNode(context).orElseThrow(MissingValueException::new);
            Number rightValue = (Number) rightOperandNode.interpretNode(context).orElseThrow(MissingValueException::new);

            switch (comparingToken.getType()) {
                case MORE:
                    result = Optional.of(leftValue.floatValue() > rightValue.floatValue());
                    break;
                case LESS:
                    result = Optional.of(leftValue.floatValue() < rightValue.floatValue());
                    break;
            }

        } else {
            result = leftOperandNode.interpretNode(context);
        }

        return result;
    }
}
