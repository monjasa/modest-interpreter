package org.monjasa.interpreter.engine.semanticanalyzer;

import com.sun.istack.internal.Nullable;
import org.monjasa.interpreter.engine.exceptions.MissingIdentifierException;
import org.monjasa.interpreter.engine.symbols.BuiltinTypeSymbol;
import org.monjasa.interpreter.engine.symbols.BuiltinValueSymbol;
import org.monjasa.interpreter.engine.symbols.Symbol;
import org.monjasa.interpreter.engine.tokens.TokenType;

import java.util.*;

public class ScopedSymbolTable {

    private Map<String, Symbol> symbols;
    private String scopeName;
    private int scopeLevel;

    @Nullable
    private ScopedSymbolTable enclosingScope;

    private void initializeBuiltins() {
        defineSymbol(new BuiltinTypeSymbol(TokenType.INTEGER_TYPE.name()));
        defineSymbol(new BuiltinTypeSymbol(TokenType.FLOAT_TYPE.name()));
        defineSymbol(new BuiltinTypeSymbol(TokenType.BOOLEAN_TYPE.name()));

        defineSymbol(new BuiltinValueSymbol("true", fetchSymbol(TokenType.BOOLEAN_TYPE.name())));
        defineSymbol(new BuiltinValueSymbol("false", fetchSymbol(TokenType.BOOLEAN_TYPE.name())));
    }

    public ScopedSymbolTable(String scopeName, int scopeLevel) {
        this.symbols = new LinkedHashMap<>();
        this.scopeName = scopeName;
        this.scopeLevel = scopeLevel;
        this.enclosingScope = null;
        initializeBuiltins();
    }

    public ScopedSymbolTable(String scopeName, int scopeLevel, ScopedSymbolTable enclosingScope) {
        this.symbols = new LinkedHashMap<>();
        this.scopeName = scopeName;
        this.scopeLevel = scopeLevel;
        this.enclosingScope = enclosingScope;
        initializeBuiltins();
    }

    public void defineSymbol(Symbol symbol) {
        symbols.put(symbol.getName(), symbol);
    }

    public boolean containsSymbol(String symbolName) {
        return symbols.containsKey(symbolName);
    }

    public Symbol fetchSymbol(String symbolName) throws MissingIdentifierException {

        Symbol fetchingSymbol = null;

        if (symbols.containsKey(symbolName)) {
            fetchingSymbol = symbols.get(symbolName);
        } else if (enclosingScope != null){
            fetchingSymbol = enclosingScope.fetchSymbol(symbolName);
        } else {
            throw new MissingIdentifierException(symbolName);
        }

        return fetchingSymbol;
    }

    public int getScopeLevel() {
        return scopeLevel;
    }

    public ScopedSymbolTable getEnclosingScope() {
        return enclosingScope;
    }

    @Override
    public String toString() {

        StringJoiner joiner = new StringJoiner("\n -\t");
        joiner.add(String.format(Locale.US, "|--- SCOPE '%s', LEVEL = '%d' ---|", scopeName, scopeLevel));
        if (enclosingScope != null)
            joiner.add(String.format(Locale.US, "ENCLOSING SCOPE = '%s'", enclosingScope.scopeName));

        symbols.values().stream()
                .map(Object::toString)
                .forEach(joiner::add);

        return joiner.toString();
    }
}
