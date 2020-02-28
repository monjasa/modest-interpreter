package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.engine.interpreter.Context;

import java.util.Optional;

public interface AbstractNode {
    Optional<?> interpretNode(Context context);
}
