package org.monjasa.interpreter.engine.symbols;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ProcedureSymbol extends Symbol {

    private List<Symbol> formalParameters;

    public ProcedureSymbol(String name) {
        super(name);
        this.formalParameters = new ArrayList<>();
    }

    public ProcedureSymbol(String name, List<Symbol> formalParameterReferences) {
        super(name);
        this.formalParameters = new ArrayList<>(formalParameterReferences);
    }

    public void appendFormalParameter(Symbol symbol) {
        formalParameters.add(symbol);
    }

    public int getFormalParametersNumber() {
        return formalParameters.size();
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s[name = '%s'; parameters = '%s']",
                this.getClass().getSimpleName(), name, formalParameters.toString());
    }
}
