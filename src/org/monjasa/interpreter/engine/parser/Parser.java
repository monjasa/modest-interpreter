package org.monjasa.interpreter.engine.parser;

import org.monjasa.interpreter.engine.exceptions.InvalidSyntaxException;
import org.monjasa.interpreter.engine.ast.*;
import org.monjasa.interpreter.engine.exceptions.MissingValueException;
import org.monjasa.interpreter.engine.lexer.Lexer;
import org.monjasa.interpreter.engine.tokens.Token;
import org.monjasa.interpreter.engine.tokens.TokenType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        //                  | INTEGER_CONST
        //                  | FLOAT_CONST
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
            case INTEGER_CONST:
                setupCurrentToken(TokenType.INTEGER_CONST);
                node = new NumberOperandNode(token);
                break;
            case FLOAT_CONST:
                setupCurrentToken(TokenType.FLOAT_CONST);
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
        //      term : termValue ((MULTIPLICATION | INTEGER_DIVISION | FLOAT_DIVISION) termValue)*
        //

        AbstractNode node = getTermValue();

        while (Token.isTermToken(currentToken.getType())) {

            Token token = currentToken;
            switch (token.getType()) {
                case MULTIPLICATION:
                    setupCurrentToken(TokenType.MULTIPLICATION);
                    break;
                case FLOAT_DIVISION:
                    setupCurrentToken(TokenType.FLOAT_DIVISION);
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

    private AbstractNode getProcedureCallStatement() {

        //
        //      procedureCallStatement : ID LEFT_PARENTHESIS (expression (COMMA expression)*)? RIGHT_PARENTHESIS
        //

        Token procedureIdToken = currentToken;

        String procedureName = procedureIdToken.getValue(String.class)
                .orElseThrow(MissingValueException::new);
        setupCurrentToken(TokenType.ID);
        setupCurrentToken(TokenType.LEFT_PARENTHESIS);

        List<AbstractNode> parameters = new ArrayList<>();

        if (currentToken.getType() != TokenType.RIGHT_PARENTHESIS)
            parameters.add(getExpression());

        while (currentToken.getType() == TokenType.COMMA) {
            setupCurrentToken(TokenType.COMMA);
            parameters.add(getExpression());
        }

        setupCurrentToken(TokenType.RIGHT_PARENTHESIS);

        return new ProcedureCallNode(procedureName, parameters, procedureIdToken);
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
        //      statement : compoundStatement
        //                  | procedureCallStatement
        //                  | assignmentStatement
        //                  | emptyStatement
        //

        AbstractNode node;

        Token token = currentToken;
        switch (token.getType()) {
            case BEGIN:
                node = getCompoundStatement();
                break;
            case ID:
                if (lexer.getCurrentChar() == TokenType.LEFT_PARENTHESIS.getContraction())
                    node = getProcedureCallStatement();
                else
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

    private AbstractNode getTypeSpecification() {

        //
        //      typeSpecification : INTEGER_TYPE
        //                          | FLOAT_TYPE
        //

        OperandTypeNode operandTypeNode;

        switch (currentToken.getType()) {
            case INTEGER_TYPE:
                operandTypeNode = new OperandTypeNode(currentToken);
                setupCurrentToken(TokenType.INTEGER_TYPE);
                break;
            case FLOAT_TYPE:
                operandTypeNode = new OperandTypeNode(currentToken);
                setupCurrentToken(TokenType.FLOAT_TYPE);
                break;
            default:
                throw new RuntimeException();
        }

        return operandTypeNode;
    }

    private ArrayList<VariableDeclarationNode> getVariableDeclaration() {

        //
        //      variableDeclaration: ID (COMMA ID)* COLON typeSpecification
        //

        ArrayList<VariableNode> variableNodes = new ArrayList<>();
        variableNodes.add(new VariableNode(currentToken));
        setupCurrentToken(TokenType.ID);

        while (currentToken.getType() == TokenType.COMMA) {
            setupCurrentToken(TokenType.COMMA);
            variableNodes.add(new VariableNode(currentToken));
            setupCurrentToken(TokenType.ID);
        }

        setupCurrentToken(TokenType.COLON);

        OperandTypeNode typeNode = (OperandTypeNode) getTypeSpecification();
        ArrayList<VariableDeclarationNode> declarationNodes = new ArrayList<>(variableNodes.size());

        variableNodes.forEach(node -> declarationNodes.add(new VariableDeclarationNode(node, typeNode)));

        return declarationNodes;
    }

    private List<ParameterNode> getFormalParameters() {

        //
        //      formalParameters : ID (COMMA ID)* COLON typeSpecification
        //

        List<ParameterNode> parameterNodes = new ArrayList<>();
        List<Token> parameterTokens = new ArrayList<>();

        setupCurrentToken(TokenType.ID);
        while (currentToken.getType() == TokenType.COMMA) {
            setupCurrentToken(TokenType.COMMA);
            parameterTokens.add(currentToken);
            setupCurrentToken(TokenType.ID);
        }

        setupCurrentToken(TokenType.COLON);
        OperandTypeNode typeNode = (OperandTypeNode) getTypeSpecification();

        parameterTokens.forEach(token -> parameterNodes.add(new ParameterNode(new VariableNode(token), typeNode)));
        return parameterNodes;
    }

    private List<ParameterNode> getFormalParametersList() {

        //
        //      formalParametersList : formalParameters
        //                             | formalParameters SEMI formalParametersList
        //

        if (currentToken.getType() != TokenType.ID)
            return Collections.emptyList();

        List<ParameterNode> parameterNodes = getFormalParameters();

        while (currentToken.getType() == TokenType.SEMICOLON) {
            setupCurrentToken(TokenType.SEMICOLON);
            parameterNodes.addAll(getFormalParameters());
        }

        return parameterNodes;
    }

    private ArrayList<DeclarationNode> getDeclarations() {

        //
        //      declarations : VARIABLE_DECLARATION_BLOCK (variableDeclaration SEMICOLON)+
        //                     | (PROCEDURE ID (LEFT_PARENTHESIS formalParametersList RIGHT_PARENTHESIS)? SEMICOLON block SEMICOLON)*
        //                     | empty
        //

        ArrayList<DeclarationNode> declarations = new ArrayList<>();

        if (currentToken.getType() == TokenType.VARIABLE_DECLARATION_BLOCK) {
            setupCurrentToken(TokenType.VARIABLE_DECLARATION_BLOCK);

            while (currentToken.getType() == TokenType.ID) {
                declarations.addAll(getVariableDeclaration());
                setupCurrentToken(TokenType.SEMICOLON);
            }
        }

        while (currentToken.getType() == TokenType.PROCEDURE) {
            setupCurrentToken(TokenType.PROCEDURE);
            String procedureName = currentToken.getValue(String.class)
                    .orElseThrow(MissingValueException::new);
            setupCurrentToken(TokenType.ID);

            List<ParameterNode> parameters = Collections.emptyList();

            if (currentToken.getType() == TokenType.LEFT_PARENTHESIS) {
                setupCurrentToken(TokenType.LEFT_PARENTHESIS);
                parameters = getFormalParametersList();
                setupCurrentToken(TokenType.RIGHT_PARENTHESIS);
            }

            setupCurrentToken(TokenType.SEMICOLON);
            BlockNode procedureBlockNode = (BlockNode) getBlock();
            declarations.add(new ProcedureDeclarationNode(procedureName, parameters, procedureBlockNode));
            setupCurrentToken(TokenType.SEMICOLON);
        }

        return declarations;
    }

    private AbstractNode getBlock() {

        //
        //      block : declarations compoundStatement
        //

        ArrayList<DeclarationNode> declarations = getDeclarations();
        CompoundStatementNode compoundStatementNode = (CompoundStatementNode) getCompoundStatement();

        return new BlockNode(declarations, compoundStatementNode);
    }

    private AbstractNode getProgram() {

        //
        //      program : PROGRAM variable SEMICOLON block DOT
        //

        setupCurrentToken(TokenType.PROGRAM);
        String programName = ((VariableNode) getVariable()).getVariableToken().getValue(String.class)
                .orElseThrow(MissingValueException::new);

        setupCurrentToken(TokenType.SEMICOLON);
        BlockNode blockNode = (BlockNode) getBlock();

        ProgramNode programNode = new ProgramNode(programName, blockNode);
        setupCurrentToken(TokenType.DOT);
        return programNode;
    }

    public AbstractNode parseCommand() {

        AbstractNode root = getProgram();

        if (currentToken.getType() != TokenType.EOF)
            throw new RuntimeException();

        return root;
    }
}
