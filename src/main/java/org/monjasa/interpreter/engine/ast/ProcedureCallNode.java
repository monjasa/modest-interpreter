package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.callstack.ActivationRecord;
import org.monjasa.interpreter.engine.callstack.ActivationRecordType;
import org.monjasa.interpreter.engine.exceptions.WrongParametersNumberException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.interpreter.Interpreter;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;
import org.monjasa.interpreter.engine.symbols.ProcedureSymbol;
import org.monjasa.interpreter.engine.symbols.Symbol;
import org.monjasa.interpreter.engine.tokens.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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

        Interpreter.logInfo(String.format(Locale.US, "Preparing an activation record for procedure = '%s'...",
                procedureName), true);

        ActivationRecord procedureActivationRecord = new ActivationRecord(procedureName,
                ActivationRecordType.PROCEDURE, 2);

        List<Symbol> formalParameters = procedureSymbol.getFormalParameters();

        for (int i = 0; i < formalParameters.size(); i++)
            procedureActivationRecord.putMember(formalParameters.get(i).getName(), arguments.get(i).interpretNode(context));

        Interpreter.logInfo(procedureActivationRecord.toString(), true);
        Interpreter.logInfo(String.format(Locale.US, "Entering: procedure = '%s'", procedureName), true);
        context.getCallStack().pushRecord(procedureActivationRecord);
        procedureSymbol.getBlockReference().interpretNode(context);

        Interpreter.logInfo(String.format(Locale.US, "Leaving: procedure = '%s'", procedureName), true);
        context.getCallStack().popRecord();
        Interpreter.logInfo(procedureActivationRecord.toString(), true);

        return Optional.empty();
    }
}
