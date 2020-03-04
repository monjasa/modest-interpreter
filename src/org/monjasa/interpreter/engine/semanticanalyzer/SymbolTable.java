package org.monjasa.interpreter.engine.semanticanalyzer;

import org.monjasa.interpreter.engine.exceptions.MissingIdentifierException;
import org.monjasa.interpreter.engine.symbols.BuiltinTypeSymbol;
import org.monjasa.interpreter.engine.symbols.Symbol;
import org.monjasa.interpreter.engine.tokens.TokenType;

import java.util.*;

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

    public boolean containsSymbol(String symbolName) {
        return symbols.containsKey(symbolName);
    }

    public Symbol fetchSymbol(String symbolName) throws MissingIdentifierException {

//        System.out.println(String.format(Locale.US, "Fetching %s...", symbolName));
        if (!symbols.containsKey(symbolName)) throw new MissingIdentifierException(symbolName);

        return symbols.get(symbolName);
    }

    @Override
    public String toString() {

        StringJoiner joiner = new StringJoiner("\n");
        joiner.add("Symbol Table contents:");
        symbols.values().stream()
                .map(Object::toString)
                .forEach(joiner::add);

        return joiner.toString();
    }
}
