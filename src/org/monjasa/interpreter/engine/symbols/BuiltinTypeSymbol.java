package org.monjasa.interpreter.engine.symbols;

import org.monjasa.interpreter.engine.symbols.Symbol;

public class BuiltinTypeSymbol extends Symbol {

    public BuiltinTypeSymbol(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
