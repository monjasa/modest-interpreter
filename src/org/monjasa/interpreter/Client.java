package org.monjasa.interpreter;

import org.monjasa.interpreter.engine.interpreter.Interpreter;
import org.monjasa.interpreter.engine.lexer.Lexer;
import org.monjasa.interpreter.engine.parser.Parser;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Client {

    public static void main(String[] args) {

        //
        //      specify path to the file with a command to interpret
        //

        String filePath = "test/program.txt";

        try {

            String command = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            Interpreter interpreter = new Interpreter(new Parser(new Lexer(command)));
            interpreter.interpret();

            System.out.println();
            System.out.println(interpreter.getContext().getGlobalScope());

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
