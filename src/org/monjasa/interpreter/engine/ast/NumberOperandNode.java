package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.tokens.Token;

import java.util.Optional;

public class NumberOperandNode extends TerminalNode {

    private Token numberToken;

    public NumberOperandNode(Token numberToken) {
        this.numberToken = numberToken;
    }

    @Override
    public Optional<?> interpretNode(Context context) {

        // TODO : replace Integer with wild class a.k.a. ? extends Number
        Class<?> expectedClass = Integer.class;

        return numberToken.getValue(expectedClass);
    }
}
