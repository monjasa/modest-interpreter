package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;

import java.util.Optional;

public class EmptyOperatorNode extends TerminalNode {

    @Override
    public Optional<?> interpretNode(Context context) {
        return Optional.empty();
    }
}
