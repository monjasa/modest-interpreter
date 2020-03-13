package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.callstack.ActivationRecord;
import org.monjasa.interpreter.engine.callstack.ActivationRecordType;
import org.monjasa.interpreter.engine.exceptions.WrongParametersNumberException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;
import org.monjasa.interpreter.engine.symbols.ProcedureSymbol;
import org.monjasa.interpreter.engine.symbols.Symbol;
import org.monjasa.interpreter.engine.tokens.Token;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProcedureCallNode extends NonTerminalNode {

    private String procedureName;
    private List<AbstractNode> arguments;
    private Token procedureIdToken;
    private ProcedureSymbol procedureSymbol;

    public ProcedureCallNode(String procedureName, List<AbstractNode> argumentReferences, Token procedureIdToken) {
        this.procedureName = procedureName;
        this.arguments = new ArrayList<>(argumentReferences);
        this.procedureIdToken = procedureIdToken;
    }

    @Override
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {

        procedureSymbol = (ProcedureSymbol) currentScope.fetchSymbol(procedureName);
        int formalParametersCount = procedureSymbol.getFormalParameters().size();
        int actualParametersCount = arguments.size();

        if (formalParametersCount != actualParametersCount)
            throw new WrongParametersNumberException(formalParametersCount, actualParametersCount);

        arguments.forEach(parameter -> parameter.analyzeNodeSemantic(currentScope));
    }

    @Override
    public Optional<?> interpretNode(Context context) {

        ActivationRecord procedureActivationRecord = new ActivationRecord(procedureName,
                ActivationRecordType.PROCEDURE, 2);

        List<Symbol> formalParameters = procedureSymbol.getFormalParameters();

        for (int i = 0; i < formalParameters.size(); i++)
            procedureActivationRecord.putMember(formalParameters.get(i).getName(), arguments.get(i).interpretNode(context));

        System.out.println(procedureActivationRecord.toString());

        context.getCallStack().pushRecord(procedureActivationRecord);
        procedureSymbol.getBlockReference().interpretNode(context);
        context.getCallStack().popRecord();

        System.out.println(procedureActivationRecord.toString());

        return Optional.empty();
    }
}
