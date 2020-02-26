package org.monjasa.interpreter.engine.ast;

public abstract class AbstractNode {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
