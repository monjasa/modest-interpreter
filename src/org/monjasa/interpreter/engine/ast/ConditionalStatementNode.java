package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.exceptions.MissingValueException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;

import java.util.Optional;

public class ConditionalStatementNode extends NonTerminalNode {

    private ConditionNode conditionNode;
    private AbstractNode compoundStatement;
    private AbstractNode alternateCompoundStatement;

    public ConditionalStatementNode(ConditionNode conditionNode, CompoundStatementNode compoundStatement) {
        this.conditionNode = conditionNode;
        this.compoundStatement = compoundStatement;
        this.alternateCompoundStatement = new EmptyOperatorNode();
    }

    public ConditionalStatementNode(ConditionNode conditionNode, CompoundStatementNode compoundStatement, CompoundStatementNode alternateCompoundStatement) {
        this.conditionNode = conditionNode;
        this.compoundStatement = compoundStatement;
        this.alternateCompoundStatement = alternateCompoundStatement;
    }

    @Override
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {

        // TODO: implement methods

        conditionNode.analyzeNodeSemantic(currentScope);
        compoundStatement.analyzeNodeSemantic(currentScope);
        alternateCompoundStatement.analyzeNodeSemantic(currentScope);
    }

    @Override
    public Optional<?> interpretNode(Context context) {

        boolean conditionValue = (Boolean) conditionNode.interpretNode(context).orElseThrow(MissingValueException::new);

        if (conditionValue) compoundStatement.interpretNode(context);
        else alternateCompoundStatement.interpretNode(context);

        return Optional.empty();
    }
}
