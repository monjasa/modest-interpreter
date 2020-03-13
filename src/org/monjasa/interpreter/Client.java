package org.monjasa.interpreter;

import org.monjasa.interpreter.engine.callstack.ActivationRecord;
import org.monjasa.interpreter.engine.callstack.ActivationRecordType;
import org.monjasa.interpreter.engine.callstack.CallStack;
import org.monjasa.interpreter.engine.interpreter.Interpreter;
import org.monjasa.interpreter.engine.lexer.Lexer;
import org.monjasa.interpreter.engine.parser.Parser;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class Client extends JFrame {

    private final static String TITLE = "Interpreter Tree";

    private static DefaultMutableTreeNode currentNode;

    public Client() throws HeadlessException {

        super(TITLE);

        JPanel panel = new JPanel();
        GridLayout gridLayout = new GridLayout(0, 1);
        panel.setLayout(gridLayout);

        currentNode = new DefaultMutableTreeNode("root");
        JTree tree = new JTree(currentNode);

        tree.setShowsRootHandles(true);

        JScrollPane treeView = new JScrollPane(tree);

        panel.add(treeView);
        this.add(panel);

        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        panel.setBorder(padding);

        this.setSize(400, 600);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        //
        //      setup UI
        //

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {

                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (UnsupportedLookAndFeelException e) {
                        e.printStackTrace();
                    }

                    Client client = new Client();
                }
            });
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //
        //      specify path to the file with a command to interpret
        //

        String filePath = "test/program.txt";

        try {

            String command = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            Interpreter interpreter = new Interpreter(new Parser(new Lexer(command)));
            interpreter.interpret();

            /*System.out.println();
            System.out.println(interpreter.getContext().getCallStack());*/

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static DefaultMutableTreeNode getCurrentNode() {
        return currentNode;
    }

    public static void setCurrentNode(DefaultMutableTreeNode currentNode) {
        Client.currentNode = currentNode;
    }
}
