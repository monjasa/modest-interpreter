package org.monjasa.interpreter;

import org.monjasa.interpreter.engine.Interpreter;
import org.monjasa.interpreter.engine.Lexer;

public class Client {

    public static void main(String[] args) {

        Interpreter interpreter = new Interpreter(new Lexer("16 / (3 + 3 * 2 - 1) * 5"));
        System.out.println(interpreter.interpret());
    }
}
