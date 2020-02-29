package org.monjasa.interpreter;

import org.monjasa.interpreter.engine.ast.AbstractNode;
import org.monjasa.interpreter.engine.interpreter.Context;
import org.monjasa.interpreter.engine.interpreter.Interpreter;
import org.monjasa.interpreter.engine.lexer.Lexer;
import org.monjasa.interpreter.engine.parser.Parser;

import java.util.StringJoiner;

public class Client {

    public static void main(String[] args) {

        StringJoiner command = new StringJoiner(" ");

        command.add("program MyProgram;");
        command.add("let");
        command.add("    x, y : int;");
        command.add("    z, sum : float;");
        command.add("do");
        command.add("    x = 6;");
        command.add("    y = 3 * (x + 1);");
        command.add("    z = -x / 4.0;");
        command.add("    sum = x + y + z;");
        command.add("end.");

        Interpreter interpreter = new Interpreter(new Parser(new Lexer(command.toString())));
        interpreter.interpret();
        System.out.println(interpreter.getContext().getGlobalScope());
    }
}
