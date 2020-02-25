package org.monjasa.interpreter.engine.parser;

import org.monjasa.interpreter.engine.InvalidSyntaxException;
import org.monjasa.interpreter.engine.ast.UnaryOperatorNode;
import org.monjasa.interpreter.engine.lexer.Lexer;
import org.monjasa.interpreter.engine.ast.AbstractNode;
import org.monjasa.interpreter.engine.ast.BinaryOperatorNode;
import org.monjasa.interpreter.engine.ast.NumberOperandNode;
import org.monjasa.interpreter.engine.tokens.Token;
import org.monjasa.interpreter.engine.tokens.TokenType;

public class Parser {

    private Lexer lexer;
    private Token currentToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.getNextToken();
    }

    private void setupCurrentToken(TokenType expectedTokenType) {
        if (currentToken.getType() == expectedTokenType)
            currentToken = lexer.getNextToken();
        else
            throw new InvalidSyntaxException();
    }

    private AbstractNode getTermValue() {

        //
        //      termValue : (ADDITION | SUBTRACTION) termValue | INTEGER | LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
        //

        AbstractNode node = null;

        Token token = currentToken;
        switch (token.getType()) {
            case ADDITION:
                setupCurrentToken(TokenType.ADDITION);
                node = new UnaryOperatorNode(token, getTermValue());
                break;
            case SUBTRACTION:
                setupCurrentToken(TokenType.SUBTRACTION);
                node = new UnaryOperatorNode(token, getTermValue());
                break;
            case INTEGER:
                setupCurrentToken(TokenType.INTEGER);
                node = new NumberOperandNode(token);
                break;
            case LEFT_PARENTHESIS:
                setupCurrentToken(TokenType.LEFT_PARENTHESIS);
                node = getExpression();
                setupCurrentToken(TokenType.RIGHT_PARENTHESIS);
                break;
        }

        return node;
    }

    private AbstractNode getTerm() {

        //
        //      term : termValue ((MULTIPLICATION | DIVISION) termValue)*
        //

        AbstractNode node = getTermValue();

        while (Token.isTermToken(currentToken.getType())) {

            Token token = currentToken;
            switch (token.getType()) {
                case MULTIPLICATION:
                    setupCurrentToken(TokenType.MULTIPLICATION);
                    break;
                case DIVISION:
                    setupCurrentToken(TokenType.DIVISION);
                    break;
            }

            node = new BinaryOperatorNode(node, token, getTermValue());
        }

        return node;
    }

    public AbstractNode getExpression() {

        //
        //      expression : term ((ADDITION | SUBTRACTION) term)*
        //

        AbstractNode node = getTerm();

        while (Token.isInterpretToken(currentToken.getType())) {

            Token token = currentToken;
            switch (token.getType()) {
                case ADDITION:
                    setupCurrentToken(TokenType.ADDITION);
                    break;
                case SUBTRACTION:
                    setupCurrentToken(TokenType.SUBTRACTION);
                    break;
            }

            node = new BinaryOperatorNode(node, token, getTerm());
        }

        return node;
    }

    public AbstractNode parseCommand() {
        return getExpression();
    }
}
