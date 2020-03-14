package org.monjasa.interpreter.engine.symbols;

public class BuiltinValueSymbol extends Symbol {

    public BuiltinValueSymbol(String name, Symbol type) {
        super(name, type);
    }

    @Override
    public String toString() {
        return "BuiltinValueSymbol{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
