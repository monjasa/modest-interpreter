package org.monjasa.interpreter;

import org.monjasa.interpreter.engine.ast.AbstractNode;
import org.monjasa.interpreter.engine.ast.CompoundStatementNode;
import org.monjasa.interpreter.engine.interpreter.Interpreter;
import org.monjasa.interpreter.engine.lexer.Lexer;
import org.monjasa.interpreter.engine.parser.Parser;
import org.monjasa.interpreter.engine.tokens.Token;
import org.monjasa.interpreter.engine.tokens.TokenType;

import java.util.StringJoiner;

public class Client {

    public static void main(String[] args) {

        /*
        Interpreter interpreter = new Interpreter(new Parser(new Lexer("-16 / (3 + 3 * 2 + (-1)) * 7 + 2")));
        System.out.println(interpreter.interpret());
        */

        StringJoiner command = new StringJoiner(" ");

        command.add("do");
        command.add("   a = 2;");
        command.add("   b = 3;");
        command.add("   c = b + 2 * a;");
        command.add("end.");

        Interpreter interpreter = new Interpreter(new Parser(new Lexer(command.toString())));
        interpreter.interpret();
        System.out.println(interpreter.getGlobalScope());
    }
}
