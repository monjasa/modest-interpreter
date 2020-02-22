package org.monjasa.interpreter.engine;

import org.monjasa.interpreter.engine.tokens.Token;
import org.monjasa.interpreter.engine.tokens.TokenType;

public class Interpreter {

    private Lexer lexer;
    private Token currentToken;

    public Interpreter(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.getNextToken();
    }

    private void setupCurrentToken(TokenType expectedTokenType) {
        if (currentToken.getType() == expectedTokenType)
            currentToken = lexer.getNextToken();
        else
            throw new InvalidSyntaxException();
    }

    private String getTermValue() {

        //
        //      termValue : INTEGER | LEFT_PARENTHESIS interpret RIGHT_PARENTHESIS
        //

        String result = null;

        Token token = currentToken;
        switch (token.getType()) {
            case INTEGER:
                setupCurrentToken(TokenType.INTEGER);
                result = token.getValue();
                break;
            case LEFT_PARENTHESIS:
                setupCurrentToken(TokenType.LEFT_PARENTHESIS);
                result = interpret();
                setupCurrentToken(TokenType.RIGHT_PARENTHESIS);
                break;
        }

        return result;
    }

    private String getTerm() {

        //
        //      term : termValue ((MULTIPLICATION | DIVISION) termValue)*
        //

        String result = getTermValue();

        while (Token.isTermToken(currentToken.getType())) {

            Token token = currentToken;
            switch (token.getType()) {
                case MULTIPLICATION:
                    setupCurrentToken(TokenType.MULTIPLICATION);
                    result = String.valueOf(Integer.parseInt(result) * Integer.parseInt(getTermValue()));
                    break;
                case DIVISION:
                    setupCurrentToken(TokenType.DIVISION);
                    result = String.valueOf(Integer.parseInt(result) / Integer.parseInt(getTermValue()));
                    break;
            }
        }

        return result;
    }

    public String interpret() {

        //
        //      interpret : term ((ADDITION | SUBTRACTION) term)*
        //

        String result = getTerm();

        while (Token.isInterpretToken(currentToken.getType())) {

            Token token = currentToken;
            switch (token.getType()) {
                case ADDITION:
                    setupCurrentToken(TokenType.ADDITION);
                    result = String.valueOf(Integer.parseInt(result) + Integer.parseInt(getTerm()));
                    break;
                case SUBTRACTION:
                    setupCurrentToken(TokenType.SUBTRACTION);
                    result = String.valueOf(Integer.parseInt(result) - Integer.parseInt(getTerm()));
                    break;
            }
        }

        return result;
    }
}
