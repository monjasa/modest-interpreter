package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.Client;
import org.monjasa.interpreter.engine.callstack.ActivationRecord;
import org.monjasa.interpreter.engine.callstack.ActivationRecordType;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;

import javax.swing.tree.DefaultMutableTreeNode;
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
        System.out.println("Enter scope: global");
        blockNode.analyzeNodeSemantic(currentScope);
        System.out.println(currentScope);
        System.out.println("Leave scope: global");
    }

    @Override
    public Optional<?> interpretNode(Context context) {

        ActivationRecord programActivationRecord = new ActivationRecord(programName,
                ActivationRecordType.PROGRAM, 1);

        System.out.println(String.format(Locale.US, "Enter: Program='%s'", programName));
        context.getCallStack().pushRecord(programActivationRecord);

        blockNode.interpretNode(context);
        System.out.println(programActivationRecord);

        System.out.println(String.format(Locale.US, "Leave: Program='%s'", programName));
        context.getCallStack().popRecord();

        return Optional.empty();
    }

    @Override
    public String toString() {
        return "ProgramNode{" +
                "programName='" + programName + '\'' +
                '}';
    }
}
