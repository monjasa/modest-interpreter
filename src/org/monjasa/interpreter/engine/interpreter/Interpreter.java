package org.monjasa.interpreter.engine.interpreter;

import org.monjasa.interpreter.engine.ast.*;
import org.monjasa.interpreter.engine.parser.Parser;
import org.monjasa.interpreter.engine.semanticanalyzer.SemanticAnalyzer;

import java.util.Optional;

public class Interpreter {

    private Parser parser;
    private SemanticAnalyzer semanticAnalyzer;
    private Context context;

    public Interpreter(Parser parser) {
        this.parser = parser;
        this.semanticAnalyzer = new SemanticAnalyzer();
        this.context = new Context();
    }

    public Optional<?> interpret() {
        AbstractNode syntaxTreeRoot = parser.parseCommand();
        semanticAnalyzer.analyzeSemantic(syntaxTreeRoot);
        //return syntaxTreeRoot.interpretNode(context);
        return Optional.empty();
    }

    public SemanticAnalyzer getSemanticAnalyzer() {
        return semanticAnalyzer;
    }

    public Context getContext() {
        return context;
    }
}
