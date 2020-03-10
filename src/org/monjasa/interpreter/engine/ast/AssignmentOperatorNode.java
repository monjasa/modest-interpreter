package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.callstack.ActivationRecord;
import org.monjasa.interpreter.engine.exceptions.MissingValueException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;
import org.monjasa.interpreter.engine.tokens.Token;
import org.monjasa.interpreter.engine.tokens.TokenType;

import java.util.Optional;

public class AssignmentOperatorNode extends NonTerminalNode {

    private VariableNode variableNode;
    private Token assignmentToken;
    private AbstractNode operand;

    public AssignmentOperatorNode(VariableNode variableNode, AbstractNode operand) {
        this.variableNode = variableNode;
        this.assignmentToken = new Token(TokenType.ASSIGNMENT, "=");
        this.operand = operand;
    }

    @Override
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {

        // TODO : replace try-catch within the method
        String variableName = variableNode.getVariableToken().getValue(String.class)
                .orElseThrow(MissingValueException::new);

        currentScope.fetchSymbol(variableName);
        operand.analyzeNodeSemantic(currentScope);
    }

    @Override
    public Optional<?> interpretNode(Context context) {

        String variableName = variableNode.getVariableToken().getValue(String.class)
                .orElseThrow(MissingValueException::new);
        Optional<?> variableValue = operand.interpretNode(context);

        ActivationRecord activationRecord = context.getCallStack().peekRecord();
        activationRecord.putMember(variableName, variableValue);

        return Optional.empty();
    }
}
