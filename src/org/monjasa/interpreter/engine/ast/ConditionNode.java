package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;
import org.monjasa.interpreter.engine.tokens.Token;

import java.util.Optional;

public class ConditionNode extends NonTerminalNode {

    private AbstractNode conditionNode;

    public ConditionNode(AbstractNode conditionNode) {
        this.conditionNode = conditionNode;
    }

    @Override
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {

        // TODO: implement methods

        conditionNode.analyzeNodeSemantic(currentScope);
    }

    @Override
    public Optional<?> interpretNode(Context context) {
        return conditionNode.interpretNode(context);
    }
}
