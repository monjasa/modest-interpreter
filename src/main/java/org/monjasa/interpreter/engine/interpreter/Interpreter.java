package org.monjasa.interpreter.engine.interpreter;

import org.monjasa.interpreter.Client;
import org.monjasa.interpreter.engine.ast.AbstractNode;
import org.monjasa.interpreter.engine.parser.Parser;
import org.monjasa.interpreter.engine.semanticanalyzer.SemanticAnalyzer;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Interpreter {

    private static final String LOGGING_START_MESSAGE = "Interpreting begins...";
    private static final String LOGGING_END_MESSAGE = "Interpreting ends.";
    private static final String LOGGING_FORMAT = "Interpreter | %s";

    private final static Logger LOGGER = Logger.getLogger(Interpreter.class.getName());

    static {
        LOGGER.setLevel(Level.INFO);
    }

    public static void logInfo(String stringMessage, boolean isFormatted) {

        if (isFormatted)
            LOGGER.info(String.format(Locale.US, LOGGING_FORMAT, stringMessage) + '\n');
        else
            LOGGER.info(stringMessage + '\n');
    }

    private Parser parser;
    private SemanticAnalyzer semanticAnalyzer;
    private Context context;

    public Interpreter(Client client, Parser parser) {
        this.parser = parser;
        this.semanticAnalyzer = new SemanticAnalyzer();
        this.context = new Context(client);
    }

    public void interpret() {

        AbstractNode syntaxTreeRoot = parser.parseCommand();
        semanticAnalyzer.analyzeSemantic(syntaxTreeRoot);

        logInfo(LOGGING_START_MESSAGE, false);
        syntaxTreeRoot.interpretNode(context);
        logInfo(LOGGING_END_MESSAGE, false);
    }

    public SemanticAnalyzer getSemanticAnalyzer() {
        return semanticAnalyzer;
    }

    public Context getContext() {
        return context;
    }
}
