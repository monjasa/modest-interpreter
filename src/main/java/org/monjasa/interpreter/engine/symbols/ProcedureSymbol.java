package org.monjasa.interpreter.engine.symbols;

import jdk.nashorn.internal.ir.Block;
import org.monjasa.interpreter.Client;
import org.monjasa.interpreter.engine.ast.AbstractNode;
import org.monjasa.interpreter.engine.ast.BlockNode;
import org.monjasa.interpreter.engine.ast.CompoundStatementNode;
import org.monjasa.interpreter.engine.ast.ExternalCallNode;
import org.monjasa.interpreter.engine.tokens.TokenType;

import java.util.*;
import java.util.function.BiConsumer;

public class ProcedureSymbol<T> extends Symbol {

    private List<Symbol> formalParameters;
    private BlockNode blockReference;

    public ProcedureSymbol(String name, BlockNode blockReference) {
        super(name);
        this.formalParameters = new ArrayList<>();
        this.blockReference = blockReference;
    }

    public ProcedureSymbol(String name, List<Symbol> formalParameterReferences, BlockNode blockReference) {
        super(name);
        this.formalParameters = new ArrayList<>(formalParameterReferences);
        this.blockReference = blockReference;
    }

    public ProcedureSymbol(String name, Symbol parameterType, BiConsumer<Client, T> biConsumer) {
        super(name);
        formalParameters = Collections.singletonList(new VariableSymbol("argument", parameterType));
        blockReference = new BlockNode(
                new ArrayList<>(),
                new CompoundStatementNode(Collections.singletonList(new ExternalCallNode<>(biConsumer)))
        );
    }

    public void appendFormalParameter(Symbol symbol) {
        formalParameters.add(symbol);
    }

    public List<Symbol> getFormalParameters() {
        return formalParameters;
    }

    public BlockNode getBlockReference() {
        return blockReference;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s[name = '%s'; parameters = '%s']",
                this.getClass().getSimpleName(), name, formalParameters.toString());
    }
}
