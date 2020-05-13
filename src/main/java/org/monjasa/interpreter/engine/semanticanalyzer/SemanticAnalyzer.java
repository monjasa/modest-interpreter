package org.monjasa.interpreter.engine.semanticanalyzer;

import org.monjasa.interpreter.engine.ast.AbstractNode;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SemanticAnalyzer {

    private static final String LOGGING_START_MESSAGE = "Semantic Analysis begins...";
    private static final String LOGGING_END_MESSAGE = "Semantic Analysis ends.";
    private static final String LOGGING_FORMAT = "Semantic Analyzer | %s";

    private final static Logger LOGGER = Logger.getLogger(SemanticAnalyzer.class.getName());

    static {
        LOGGER.setLevel(Level.INFO);
    }

    public static void logInfo(String stringMessage, boolean isFormatted) {

        if (isFormatted)
            LOGGER.info(String.format(Locale.US, LOGGING_FORMAT, stringMessage) + '\n');
        else
            LOGGER.info(stringMessage + '\n');
    }

    private ScopedSymbolTable scopedSymbolTable;

    public SemanticAnalyzer() {
        scopedSymbolTable = new ScopedSymbolTable("global", 1);
    }

    public void analyzeSemantic(AbstractNode syntaxTreeRoot) {
        logInfo(LOGGING_START_MESSAGE, false);
        syntaxTreeRoot.analyzeNodeSemantic(scopedSymbolTable);
        logInfo(LOGGING_END_MESSAGE, false);
    }

    public ScopedSymbolTable getScopedSymbolTable() {
        return scopedSymbolTable;
    }
}
