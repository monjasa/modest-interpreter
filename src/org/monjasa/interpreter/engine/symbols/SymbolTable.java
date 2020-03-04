package org.monjasa.interpreter.engine.symbols;

import org.monjasa.interpreter.engine.exceptions.MissingSymbolException;
import org.monjasa.interpreter.engine.tokens.TokenType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class SymbolTable {

    private Map<String, Symbol> symbols;

    private void initializeBuiltins() {
        defineSymbol(new BuiltinTypeSymbol(TokenType.INTEGER_TYPE.name()));
        defineSymbol(new BuiltinTypeSymbol(TokenType.FLOAT_TYPE.name()));
    }

    public SymbolTable() {
        this.symbols = new LinkedHashMap<>();
        initializeBuiltins();
    }

    public void defineSymbol(Symbol symbol) {
//        System.out.println(String.format(Locale.US, "Defining %s...", symbol.toString()));
        symbols.put(symbol.getName(), symbol);
    }

    public Symbol fetchSymbol(String symbolName) throws MissingSymbolException {

//        System.out.println(String.format(Locale.US, "Fetching %s...", symbolName));
        if (!symbols.containsKey(symbolName)) throw new MissingSymbolException();

        return symbols.get(symbolName);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Symbols = %s", symbols.values().toString());
    }
}
