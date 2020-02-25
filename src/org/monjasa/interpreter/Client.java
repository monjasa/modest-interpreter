package org.monjasa.interpreter;

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

        Lexer lexer = new Lexer("BEGIN a := 2; END.");
        Token currentToken = lexer.getNextToken();
        while (currentToken.getType() != TokenType.EOF) {
            System.out.println(currentToken);
            currentToken = lexer.getNextToken();
        }
    }
}
