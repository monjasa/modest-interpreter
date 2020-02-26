package org.monjasa.interpreter;

import org.monjasa.interpreter.engine.ast.AbstractNode;
import org.monjasa.interpreter.engine.ast.CompoundStatementNode;
import org.monjasa.interpreter.engine.interpreter.Interpreter;
import org.monjasa.interpreter.engine.lexer.Lexer;
import org.monjasa.interpreter.engine.parser.Parser;
import org.monjasa.interpreter.engine.tokens.Token;
import org.monjasa.interpreter.engine.tokens.TokenType;

public class Client {

    public static void main(String[] args) {

        /*
        Interpreter interpreter = new Interpreter(new Parser(new Lexer("-16 / (3 + 3 * 2 + (-1)) * 7 + 2")));
        System.out.println(interpreter.interpret());
        */

        Interpreter interpreter = new Interpreter(new Parser(new Lexer("BEGIN a := 2; b := 3; c := a + b END.")));
        interpreter.interpret();
        System.out.println(interpreter.getGlobalScope());
    }
}
