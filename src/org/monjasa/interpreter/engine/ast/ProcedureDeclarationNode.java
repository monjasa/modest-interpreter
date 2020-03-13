package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.exceptions.MissingValueException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;
import org.monjasa.interpreter.engine.symbols.BuiltinTypeSymbol;
import org.monjasa.interpreter.engine.symbols.ProcedureSymbol;
import org.monjasa.interpreter.engine.symbols.Symbol;
import org.monjasa.interpreter.engine.symbols.VariableSymbol;

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
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {
        // TODO: implement method
        ProcedureSymbol procedureSymbol = new ProcedureSymbol(procedureName, blockNode);
        currentScope.defineSymbol(procedureSymbol);

        System.out.println("Enter scope: " + procedureName);
        ScopedSymbolTable procedureScope = new ScopedSymbolTable(procedureName,
                currentScope.getScopeLevel() + 1, currentScope);

        parameters.forEach(parameterNode -> {
            String parameterName = parameterNode.getVariableNode().getVariableToken().getValue(String.class)
                    .orElseThrow(MissingValueException::new);
            Symbol parameterType = procedureScope.fetchSymbol(parameterNode.getVariableType().getTypeToken().getType().name());

            Symbol parameterSymbol = new VariableSymbol(parameterName, parameterType);
            procedureSymbol.appendFormalParameter(parameterSymbol);
            procedureScope.defineSymbol(parameterSymbol);
        });

        blockNode.analyzeNodeSemantic(procedureScope);

        System.out.println(procedureScope);
        System.out.println("Leave scope " + procedureName);
    }

    @Override
    public Optional<?> interpretNode(Context context) {
        return Optional.empty();
    }
}
