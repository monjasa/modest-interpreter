package org.monjasa.interpreter;

import org.monjasa.interpreter.engine.interpreter.Interpreter;
import org.monjasa.interpreter.engine.lexer.Lexer;
import org.monjasa.interpreter.engine.parser.Parser;

import java.util.StringJoiner;

public class Client {

    public static void main(String[] args) {

        StringJoiner command = new StringJoiner(" ");

        command.add("{");
        command.add("   a = 2;");
        command.add("   b = 3;");
        command.add("   c = b + 2 * a;");
        command.add("}.");

        Interpreter interpreter = new Interpreter(new Parser(new Lexer(command.toString())));
        interpreter.interpret();
        System.out.println(interpreter.getContext().getGlobalScope());
    }
}
