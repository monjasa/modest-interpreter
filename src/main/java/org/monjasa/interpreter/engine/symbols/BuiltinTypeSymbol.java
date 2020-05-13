package org.monjasa.interpreter.engine.symbols;

public class BuiltinTypeSymbol extends Symbol {

    public BuiltinTypeSymbol(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "BuiltinTypeSymbol{" +
                "name='" + name + '\'' +
                '}';
    }
}
