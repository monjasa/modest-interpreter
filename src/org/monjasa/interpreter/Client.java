package org.monjasa.interpreter;

import org.monjasa.interpreter.engine.symbols.BuiltinTypeSymbol;
import org.monjasa.interpreter.engine.interpreter.Interpreter;
import org.monjasa.interpreter.engine.lexer.Lexer;
import org.monjasa.interpreter.engine.parser.Parser;
import org.monjasa.interpreter.engine.symbols.SymbolTable;
import org.monjasa.interpreter.engine.symbols.VariableSymbol;
import org.monjasa.interpreter.engine.tokens.TokenType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Client {

    public static void main(String[] args) {

        try {

            String command = new String(Files.readAllBytes(Paths.get("test/program.txt")), StandardCharsets.UTF_8);
            Interpreter interpreter = new Interpreter(new Parser(new Lexer(command)));
            interpreter.interpret();

            System.out.println(interpreter.getContext().getSymbolTable());
            System.out.println(interpreter.getContext().getGlobalScope());

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        /*System.out.println('\n');

        System.out.println(intType + "\t" + floatType);

        VariableSymbol barSymbol = new VariableSymbol("bar", intType);
        VariableSymbol fooSymbol = new VariableSymbol("foo", floatType);
        System.out.println(fooSymbol + "\t" + barSymbol);

        SymbolTable symbolTable = new SymbolTable();

        symbolTable.defineSymbol(fooSymbol);

        symbolTable.defineSymbol(barSymbol);
        System.out.println(symbolTable);*/
    }
}
