package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.exceptions.InappropriateOperatorException;
import org.monjasa.interpreter.engine.exceptions.MissingValueException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;
import org.monjasa.interpreter.engine.tokens.Token;
import org.monjasa.interpreter.engine.tokens.TokenType;

import java.util.Optional;

public class LogicalExpressionNode extends NonTerminalNode {

    private AbstractNode leftNode;
    private Token operatorToken;
    private AbstractNode rightNode;

    public LogicalExpressionNode(AbstractNode leftNode) {
        this.leftNode = leftNode;
        this.operatorToken = new Token(TokenType.EMPTY_TOKEN);
        this.rightNode = new EmptyOperatorNode();
    }

    public LogicalExpressionNode(AbstractNode leftNode, Token operatorToken, AbstractNode rightNode) {
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

        boolean leftValue = (Boolean) leftNode.interpretNode(context).orElseThrow(MissingValueException::new);

        if (rightNode instanceof EmptyOperatorNode) return Optional.of(leftValue);

        boolean result;

        switch (operatorToken.getType()) {
            case AND:
                if (!leftValue) result = false;
                else result = (Boolean) rightNode.interpretNode(context).orElseThrow(MissingValueException::new);
                break;
            case OR:
                if (leftValue) result = true;
                else result = (Boolean) rightNode.interpretNode(context).orElseThrow(MissingValueException::new);
                break;
            default:
                throw new InappropriateOperatorException();
        }

        return Optional.of(result);
    }

    public AbstractNode getLeftNode() {
        return leftNode;
    }
}
