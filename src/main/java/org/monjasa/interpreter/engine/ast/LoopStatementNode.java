package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.exceptions.MissingValueException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;

import java.util.Optional;

public class LoopStatementNode extends NonTerminalNode {

    private LogicalExpressionNode logicalExpressionNode;
    private CompoundStatementNode compoundStatement;

    public LoopStatementNode(LogicalExpressionNode logicalExpressionNode, CompoundStatementNode compoundStatement) {
        this.logicalExpressionNode = logicalExpressionNode;
        this.compoundStatement = compoundStatement;
    }

    @Override
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {

        // TODO: implement methods

        logicalExpressionNode.analyzeNodeSemantic(currentScope);
        compoundStatement.analyzeNodeSemantic(currentScope);
    }

    @Override
    public Optional<?> interpretNode(Context context) {

        boolean conditionValue = (Boolean) logicalExpressionNode.interpretNode(context).orElseThrow(MissingValueException::new);

        while (conditionValue) {
            compoundStatement.interpretNode(context);
            conditionValue = (Boolean) logicalExpressionNode.interpretNode(context).orElseThrow(MissingValueException::new);
            System.out.println("iteration");
        }

        return Optional.empty();
    }
}
