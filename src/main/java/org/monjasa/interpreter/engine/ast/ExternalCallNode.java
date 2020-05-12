package org.monjasa.interpreter.engine.ast;

import org.monjasa.interpreter.Client;
import org.monjasa.interpreter.engine.exceptions.MissingValueException;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.semanticanalyzer.ScopedSymbolTable;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.BiConsumer;

public class ExternalCallNode<T> extends NonTerminalNode {

    private BiConsumer<Client, T> biConsumer;

    public ExternalCallNode(BiConsumer<Client, T> biConsumer) {
        this.biConsumer = biConsumer;
    }

    @Override
    public void analyzeNodeSemantic(ScopedSymbolTable currentScope) {
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<?> interpretNode(Context context) {

        biConsumer.accept(
                context.getClient(),
                (T) context.getCallStack().peekRecord().getMember("argument").orElseThrow(MissingValueException::new));

        return Optional.empty();
    }
}
