package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.SymbolTable;

import java.util.Optional;

public class ProgramNode extends NonTerminalNode {

    private String programName;
    private BlockNode blockNode;

    public ProgramNode(String programName, BlockNode blockNode) {
        this.programName = programName;
        this.blockNode = blockNode;
    }

    @Override
    public void analyzeNodeSemantic(SymbolTable symbolTable) {
        blockNode.analyzeNodeSemantic(symbolTable);
    }

    @Override
    public Optional<?> interpretNode(Context context) {
        blockNode.interpretNode(context);
        return Optional.empty();
    }
}
