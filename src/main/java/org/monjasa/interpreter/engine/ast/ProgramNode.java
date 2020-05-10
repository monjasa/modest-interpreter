package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.callstack.ActivationRecord;
import org.monjasa.interpreter.engine.callstack.ActivationRecordType;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.interpreter.Interpreter;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;
import org.monjasa.interpreter.engine.semanticanalyzer.SemanticAnalyzer;

import java.util.Locale;
import java.util.Optional;

public class ProgramNode extends NonTerminalNode {

    private String programName;
    private BlockNode blockNode;

    public ProgramNode(String programName, BlockNode blockNode) {
        this.programName = programName;
        this.blockNode = blockNode;
    }

    @Override
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {

        SemanticAnalyzer.logInfo("Entering scope: GLOBAL", true);

        blockNode.analyzeNodeSemantic(currentScope);

        SemanticAnalyzer.logInfo(currentScope.toString(), false);
        SemanticAnalyzer.logInfo("Leaving scope: GLOBAL", true);
    }

    @Override
    public Optional<?> interpretNode(Context context) {

        Interpreter.logInfo(String.format(Locale.US, "Preparing an activation record for program = '%s'...",
                programName), true);

        ActivationRecord programActivationRecord = new ActivationRecord(programName,
                ActivationRecordType.PROGRAM, 1);

        Interpreter.logInfo(String.format(Locale.US, "Entering: program = '%s'", programName), true);
        context.getCallStack().pushRecord(programActivationRecord);

        blockNode.interpretNode(context);

        Interpreter.logInfo(String.format(Locale.US, "Leaving: program = '%s'", programName), true);
        context.getCallStack().popRecord();
        Interpreter.logInfo(programActivationRecord.toString(), true);

        return Optional.empty();
    }

    @Override
    public String toString() {
        return "ProgramNode{" +
                "programName='" + programName + '\'' +
                '}';
    }
}
