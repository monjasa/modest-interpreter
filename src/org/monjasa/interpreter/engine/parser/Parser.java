package org.monjasa.interpreter.engine.parser;

import org.monjasa.interpreter.engine.exceptions.InvalidSyntaxException;
import org.monjasa.interpreter.engine.ast.*;
import org.monjasa.interpreter.engine.lexer.Lexer;
import org.monjasa.interpreter.engine.tokens.Token;
import org.monjasa.interpreter.engine.tokens.TokenType;

import java.util.ArrayList;

public class Parser {

    private Lexer lexer;
    private Token currentToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.getNextToken();
    }

    private void setupCurrentToken(TokenType expectedTokenType) {

        if (currentToken.getType() != expectedTokenType)
            throw new InvalidSyntaxException();

        currentToken = lexer.getNextToken();
    }

    private AbstractNode getVariable() {

        //
        //      variable : ID
        //

        AbstractNode node = new VariableNode(currentToken);
        setupCurrentToken(TokenType.ID);
        return node;
    }

    private AbstractNode getTermValue() {

        //
        //      termValue : ADDITION termValue
        //                  | SUBTRACTION termValue
        //                  | INTEGER
        //                  | LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
        //                  | variable
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
            default:
                node = getVariable();
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

    private AbstractNode getExpression() {

        //
        //      expression : term ((ADDITION | SUBTRACTION) term)*
        //

        AbstractNode node = getTerm();

        while (Token.isExpressionToken(currentToken.getType())) {

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

    private AbstractNode getAssignmentStatement() {

        //
        //      assignmentStatement : variable ASSIGNMENT expression
        //

        AbstractNode node;

        AbstractNode variable = getVariable();
        setupCurrentToken(TokenType.ASSIGNMENT);
        AbstractNode expression = getExpression();

        node = new AssignmentOperatorNode((VariableNode) variable, expression);
        return node;
    }

    private AbstractNode getEmptyStatement() {

        //
        //      emptyStatement :
        //

        return new EmptyOperatorNode();
    }

    private AbstractNode getStatement() {

        //
        //      statement : compoundStatement | assignmentStatement | emptyStatement
        //

        AbstractNode node;

        Token token = currentToken;
        switch (token.getType()) {
            case BEGIN:
                node = getCompoundStatement();
                break;
            case ID:
                node = getAssignmentStatement();
                break;
            default:
                node = getEmptyStatement();
                break;
        }

        return node;
    }

    private ArrayList<AbstractNode> getStatementList() {

        //
        //      statementList : statement | statement SEMICOLON statementList
        //

        AbstractNode node = getStatement();

        ArrayList<AbstractNode> statementList = new ArrayList<>();
        statementList.add(node);

        while(currentToken.getType() == TokenType.SEMICOLON) {
            setupCurrentToken(TokenType.SEMICOLON);
            statementList.add(getStatement());
        }

        return statementList;
    }

    private AbstractNode getCompoundStatement() {

        //
        //      compoundStatement : BEGIN statementList END
        //

        setupCurrentToken(TokenType.BEGIN);
        ArrayList<AbstractNode> nodes = getStatementList();
        setupCurrentToken(TokenType.END);

        return new CompoundStatementNode(nodes);
    }

    private AbstractNode getProgram() {

        //
        //      program : compoundStatement DOT
        //

        AbstractNode node = getCompoundStatement();
        setupCurrentToken(TokenType.DOT);
        return node;
    }

    public AbstractNode parseCommand() {

        //AbstractNode root = getProgram();
        AbstractNode root = getProgram();

        if (currentToken.getType() != TokenType.EOF)
            throw new RuntimeException();

        return root;
    }
}
