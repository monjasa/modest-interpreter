package org.monjasa.interpreter.engine.symbols;

import jdk.nashorn.internal.ir.Block;
import org.monjasa.interpreter.engine.ast.BlockNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ProcedureSymbol extends Symbol {

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
