package org.monjasa.interpreter.engine.parser;

import org.monjasa.interpreter.engine.exceptions.InvalidSyntaxException;
import org.monjasa.interpreter.engine.ast.*;
import org.monjasa.interpreter.engine.exceptions.MissingValueException;
import org.monjasa.interpreter.engine.lexer.Lexer;
import org.monjasa.interpreter.engine.semanticanalyzer.SemanticAnalyzer;
import org.monjasa.interpreter.engine.tokens.Token;
import org.monjasa.interpreter.engine.tokens.TokenType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {

    private static final String LOGGING_START_MESSAGE = "Parsing begins...";
    private static final String LOGGING_END_MESSAGE = "Parsing ends.";

    private final static Logger LOGGER = Logger.getLogger(SemanticAnalyzer.class.getName());

    static {
        LOGGER.setLevel(Level.INFO);
    }

    public static void logInfo(String stringMessage) {
        LOGGER.info(stringMessage + '\n');
    }

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

    private VariableNode getVariable() {

        //
        //      variable : ID
        //

        VariableNode node = new VariableNode(currentToken);
        setupCurrentToken(TokenType.ID);
        return node;
    }

    private AbstractNode getTermValue() {

        //
        //      termValue : ADDITION termValue
        //                  | SUBTRACTION termValue
        //                  | INTEGER_CONST
        //                  | FLOAT_CONST
        //                  | TRUE_CONST
        //                  | FALSE_CONST
        //                  | LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
        //                  | variable
        //


        AbstractNode node;

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
            case TRUE_CONST:
                setupCurrentToken(TokenType.TRUE_CONST);
                node = new BooleanOperandNode(new Token(TokenType.TRUE_CONST, Boolean.TRUE));
                break;
            case FALSE_CONST:
                setupCurrentToken(TokenType.FALSE_CONST);
                node = new BooleanOperandNode(new Token(TokenType.FALSE_CONST, Boolean.FALSE));
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

    private ProcedureCallNode getProcedureCallStatement() {

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

    private AssignmentOperatorNode getAssignmentStatement() {

        //
        //      assignmentStatement : variable ASSIGNMENT expression
        //

        VariableNode variable = getVariable();
        setupCurrentToken(TokenType.ASSIGNMENT);
        AbstractNode expression = getExpression();

        return new AssignmentOperatorNode(variable, expression);
    }

    private LogicalValueNode getLogicalValue() {

        //
        //      logicalValue : TRUE_CONST
        //                     | FALSE_CONST
        //                     | variable
        //

        LogicalValueNode logicalValueNode;

        switch (currentToken.getType()) {
            case TRUE_CONST:
                setupCurrentToken(TokenType.TRUE_CONST);
                logicalValueNode = new LogicalValueNode(new BooleanOperandNode(new Token(TokenType.TRUE_CONST, Boolean.TRUE)));
                break;
            case FALSE_CONST:
                setupCurrentToken(TokenType.FALSE_CONST);
                logicalValueNode = new LogicalValueNode(new BooleanOperandNode(new Token(TokenType.FALSE_CONST, Boolean.FALSE)));
                break;
            default:
                VariableNode variableNode = getVariable();
                logicalValueNode = new LogicalValueNode(variableNode);
        }

        return logicalValueNode;
    }

    private LogicalExpressionNode getLogicalExpression() {

        //
        //      logicalExpression : logicalValue
        //                          | termValue (MORE | LESS) termValue
        //

        LogicalExpressionNode logicalExpressionNode;

        TokenType nextTokenType = lexer.peekNextToken();

        if (Token.isComparingToken(nextTokenType)) {

            AbstractNode leftOperandNode = getTermValue();

            switch (nextTokenType) {
                case MORE:
                    setupCurrentToken(TokenType.MORE);
                    break;
                case LESS:
                    setupCurrentToken(TokenType.LESS);
                    break;
            }

            logicalExpressionNode = new LogicalExpressionNode(leftOperandNode, new Token(nextTokenType), getTermValue());

        } else {
            logicalExpressionNode = new LogicalExpressionNode(getLogicalValue());
        }

        return logicalExpressionNode;
    }

    private ConditionalStatementNode getConditionalStatement() {

        //
        //      conditionalStatement : IF LEFT_PARENTHESIS logicalExpression RIGHT_PARENTHESIS compoundStatement (ELSE compoundStatement)?
        //

        ConditionalStatementNode conditionalStatementNode;
        setupCurrentToken(TokenType.IF);

        setupCurrentToken(TokenType.LEFT_PARENTHESIS);
        LogicalExpressionNode logicalExpression = getLogicalExpression();
        setupCurrentToken(TokenType.RIGHT_PARENTHESIS);

        CompoundStatementNode compoundStatement = getCompoundStatement();

        if (currentToken.getType() == TokenType.ELSE) {

            setupCurrentToken(TokenType.ELSE);

            CompoundStatementNode alternateCompoundStatement = getCompoundStatement();
            conditionalStatementNode = new ConditionalStatementNode(logicalExpression, compoundStatement, alternateCompoundStatement);

        } else {
            conditionalStatementNode = new ConditionalStatementNode(logicalExpression, compoundStatement);
        }

        return conditionalStatementNode;
    }

    private LoopStatementNode getLoopStatement() {

        //
        //      loopStatement : WHILE LEFT_PARENTHESIS logicalExpression RIGHT_PARENTHESIS compoundStatement
        //

        setupCurrentToken(TokenType.WHILE);

        setupCurrentToken(TokenType.LEFT_PARENTHESIS);
        LogicalExpressionNode logicalExpression = getLogicalExpression();
        setupCurrentToken(TokenType.RIGHT_PARENTHESIS);

        CompoundStatementNode compoundStatementNode = getCompoundStatement();

        return new LoopStatementNode(logicalExpression, compoundStatementNode);
    }

    private EmptyOperatorNode getEmptyStatement() {

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
        //                  | conditionalStatement
        //                  | loopStatement
        //                  | emptyStatement
        //

        AbstractNode node;

        Token token = currentToken;
        switch (token.getType()) {
            case BEGIN:
                node = getCompoundStatement();
                break;
            case ID:
                if (lexer.getCurrentChar() == TokenType.LEFT_PARENTHESIS.getContraction().charAt(0))
                    node = getProcedureCallStatement();
                else
                    node = getAssignmentStatement();
                break;
            case IF:
                node = getConditionalStatement();
                break;
            case WHILE:
                node = getLoopStatement();
                break;
            default:
                node = getEmptyStatement();
                break;
        }

        return node;
    }

    private List<AbstractNode> getStatementList() {

        //
        //      statementList : statement | statement SEMICOLON statementList
        //

        AbstractNode node = getStatement();

        List<AbstractNode> statementList = new ArrayList<>();
        statementList.add(node);

        while(currentToken.getType() == TokenType.SEMICOLON) {
            setupCurrentToken(TokenType.SEMICOLON);
            statementList.add(getStatement());
        }

        return statementList;
    }

    private CompoundStatementNode getCompoundStatement() {

        //
        //      compoundStatement : BEGIN statementList END
        //

        setupCurrentToken(TokenType.BEGIN);
        List<AbstractNode> nodes = getStatementList();
        setupCurrentToken(TokenType.END);

        return new CompoundStatementNode(nodes);
    }

    private OperandTypeNode getTypeSpecification() {

        //
        //      typeSpecification : INTEGER_TYPE
        //                          | FLOAT_TYPE
        //                          | BOOLEAN_TYPE
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
            case BOOLEAN_TYPE:
                operandTypeNode = new OperandTypeNode(currentToken);
                setupCurrentToken(TokenType.BOOLEAN_TYPE);
                break;
            default:
                throw new RuntimeException();
        }

        return operandTypeNode;
    }

    private List<VariableDeclarationNode> getVariableDeclaration() {

        //
        //      variableDeclaration: ID (COMMA ID)* COLON typeSpecification
        //

        List<VariableNode> variableNodes = new ArrayList<>();
        variableNodes.add(new VariableNode(currentToken));
        setupCurrentToken(TokenType.ID);

        while (currentToken.getType() == TokenType.COMMA) {
            setupCurrentToken(TokenType.COMMA);
            variableNodes.add(new VariableNode(currentToken));
            setupCurrentToken(TokenType.ID);
        }

        setupCurrentToken(TokenType.COLON);

        OperandTypeNode typeNode = getTypeSpecification();
        List<VariableDeclarationNode> declarationNodes = new ArrayList<>(variableNodes.size());

        variableNodes.forEach(node -> declarationNodes.add(new VariableDeclarationNode(node, typeNode)));

        return declarationNodes;
    }

    private List<ParameterNode> getFormalParameters() {

        //
        //      formalParameters : ID (COMMA ID)* COLON typeSpecification
        //

        List<ParameterNode> parameterNodes = new ArrayList<>();
        List<Token> parameterTokens = new ArrayList<>();

        parameterTokens.add(currentToken);
        setupCurrentToken(TokenType.ID);

        while (currentToken.getType() == TokenType.COMMA) {
            setupCurrentToken(TokenType.COMMA);
            parameterTokens.add(currentToken);
            setupCurrentToken(TokenType.ID);
        }

        setupCurrentToken(TokenType.COLON);
        OperandTypeNode typeNode = getTypeSpecification();

        parameterTokens.forEach(token -> parameterNodes.add(new ParameterNode(new VariableNode(token), typeNode)));
        return parameterNodes;
    }

    private List<ParameterNode> getFormalParametersList() {

        //
        //      formalParametersList : formalParameters
        //                             | formalParameters SEMICOLON formalParametersList
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

    private ProcedureDeclarationNode getProcedureDeclaration() {

        //
        //      procedureDeclaration : PROCEDURE ID (LEFT_PARENTHESIS formalParametersList RIGHT_PARENTHESIS)? SEMICOLON block SEMICOLON
        //

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
        BlockNode procedureBlockNode = getBlock();
        setupCurrentToken(TokenType.SEMICOLON);

        return new ProcedureDeclarationNode(procedureName, parameters, procedureBlockNode);
    }

    private List<DeclarationNode> getDeclarations() {

        //
        //      declarations : (VARIABLE_DECLARATION_BLOCK (variableDeclaration SEMICOLON)+)? procedureDeclaration*
        //

        List<DeclarationNode> declarations = new ArrayList<>();

        if (currentToken.getType() == TokenType.VARIABLE_DECLARATION_BLOCK) {

            setupCurrentToken(TokenType.VARIABLE_DECLARATION_BLOCK);

            while (currentToken.getType() == TokenType.ID) {
                declarations.addAll(getVariableDeclaration());
                setupCurrentToken(TokenType.SEMICOLON);
            }
        }

        while (currentToken.getType() == TokenType.PROCEDURE) {
            declarations.add(getProcedureDeclaration());
        }

        return declarations;
    }

    private BlockNode getBlock() {

        //
        //      block : declarations compoundStatement
        //

        List<DeclarationNode> declarations = getDeclarations();
        CompoundStatementNode compoundStatementNode = getCompoundStatement();

        return new BlockNode(declarations, compoundStatementNode);
    }

    private ProgramNode getProgram() {

        //
        //      program : PROGRAM variable SEMICOLON block DOT
        //

        setupCurrentToken(TokenType.PROGRAM);
        String programName = getVariable().getVariableToken().getValue(String.class)
                .orElseThrow(MissingValueException::new);

        setupCurrentToken(TokenType.SEMICOLON);
        BlockNode blockNode = getBlock();

        ProgramNode programNode = new ProgramNode(programName, blockNode);
        setupCurrentToken(TokenType.DOT);
        return programNode;
    }

    public ProgramNode parseCommand() {

        logInfo(LOGGING_START_MESSAGE);

        ProgramNode root = getProgram();

        if (currentToken.getType() != TokenType.EOF)
            throw new RuntimeException();

        logInfo(LOGGING_END_MESSAGE);

        return root;
    }
}
