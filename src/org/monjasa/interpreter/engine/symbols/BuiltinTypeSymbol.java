package org.monjasa.interpreter.engine.symbols;

import org.monjasa.interpreter.engine.symbols.Symbol;

import java.util.Locale;
import java.util.StringJoiner;

public class BuiltinTypeSymbol extends Symbol {

    public BuiltinTypeSymbol(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s[name = '%s']", this.getClass().getSimpleName(), name);
    }
}
