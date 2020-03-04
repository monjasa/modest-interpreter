package org.monjasa.interpreter.engine.symbols;

import java.util.Locale;

public class VariableSymbol extends Symbol {

    public VariableSymbol(String name, Symbol type) {
        super(name, type);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s[name = '%s'; type = '%s']",
                this.getClass().getSimpleName(), name, type.getName());
    }
}
