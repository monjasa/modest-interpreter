package org.monjasa.interpreter.engine.interpreter;

import org.monjasa.interpreter.engine.symbols.SymbolTable;

import java.util.HashMap;
import java.util.Optional;

public class Context {

    private HashMap<String, Optional<?>> globalScope;
    private SymbolTable symbolTable;

    public Context() {
        globalScope = new HashMap<>();
        symbolTable = new SymbolTable();
    }

    public HashMap<String, Optional<?>> getGlobalScope() {
        return globalScope;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }
}
