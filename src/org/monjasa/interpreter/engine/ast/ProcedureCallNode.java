package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;
import org.monjasa.interpreter.engine.tokens.Token;

import java.util.*;

public class ProcedureCallNode extends NonTerminalNode {

    private String procedureName;
    private List<AbstractNode> parameters;
    private Token procedureIdToken;

    public ProcedureCallNode(String procedureName, List<AbstractNode> parameterReferences, Token procedureIdToken) {
        this.procedureName = procedureName;
        this.parameters = new ArrayList<>(parameterReferences);
        this.procedureIdToken = procedureIdToken;
    }

    @Override
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {
        // TODO: implement method
    }

    @Override
    public Optional<?> interpretNode(Context context) {
        System.out.println(String.format(Locale.US, "Calling %s with %d parameter(s)...",
                procedureName, parameters.size()));
        return Optional.empty();
    }
}
