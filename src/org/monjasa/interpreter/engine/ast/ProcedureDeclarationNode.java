package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProcedureDeclarationNode extends DeclarationNode {

    private String procedureName;
    private List<ParameterNode> parameters;
    private BlockNode blockNode;

    public ProcedureDeclarationNode(String procedureName, List<ParameterNode> parameterReferences, BlockNode blockNode) {
        this.procedureName = procedureName;
        this.parameters = new ArrayList<>(parameterReferences);
        this.blockNode = blockNode;
    }

    @Override
    public void analyzeNodeSemantic(Context context) {
        // TODO: implement method
    }

    @Override
    public Optional<?> interpretNode(Context context) {
        blockNode.interpretNode(context);
        return Optional.empty();
    }
}
