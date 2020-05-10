package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.exceptions.MissingValueException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;

import java.util.Optional;

public class ConditionalStatementNode extends NonTerminalNode {

    private LogicalExpressionNode logicalExpressionNode;
    private AbstractNode compoundStatement;
    private AbstractNode alternateCompoundStatement;

    public ConditionalStatementNode(LogicalExpressionNode logicalExpressionNode, CompoundStatementNode compoundStatement) {
        this.logicalExpressionNode = logicalExpressionNode;
        this.compoundStatement = compoundStatement;
        this.alternateCompoundStatement = new EmptyOperatorNode();
    }

    public ConditionalStatementNode(LogicalExpressionNode logicalExpressionNode, CompoundStatementNode compoundStatement, CompoundStatementNode alternateCompoundStatement) {
        this.logicalExpressionNode = logicalExpressionNode;
        this.compoundStatement = compoundStatement;
        this.alternateCompoundStatement = alternateCompoundStatement;
    }

    @Override
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {

        // TODO: implement methods

        logicalExpressionNode.analyzeNodeSemantic(currentScope);
        compoundStatement.analyzeNodeSemantic(currentScope);
        alternateCompoundStatement.analyzeNodeSemantic(currentScope);
    }

    @Override
    public Optional<?> interpretNode(Context context) {

        boolean conditionValue = (Boolean) logicalExpressionNode.interpretNode(context).orElseThrow(MissingValueException::new);

        if (conditionValue) compoundStatement.interpretNode(context);
        else alternateCompoundStatement.interpretNode(context);

        return Optional.empty();
    }
}
