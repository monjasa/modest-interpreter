package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;

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
        blockNode.interpretNode(context);
        return Optional.empty();
    }
}
