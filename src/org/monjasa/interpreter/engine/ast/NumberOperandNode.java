package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.exceptions.UndefinedTypeException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.SymbolTable;
import org.monjasa.interpreter.engine.tokens.Token;

import java.util.Optional;

public class NumberOperandNode extends TerminalNode {

    private Token numberToken;

    public NumberOperandNode(Token numberToken) {
        this.numberToken = numberToken;
    }

    @Override
    public void analyzeNodeSemantic(SymbolTable symbolTable) {

    }

    @Override
    public Optional<?> interpretNode(Context context) {

        Class<? extends Number> expectedClass;

        switch (numberToken.getType()) {
            case INTEGER_CONST:
                expectedClass = Integer.class;
                break;
            case FLOAT_CONST:
                expectedClass = Float.class;
                break;
            default:
                throw new UndefinedTypeException();
        }

        return numberToken.getValue(expectedClass);
    }
}
