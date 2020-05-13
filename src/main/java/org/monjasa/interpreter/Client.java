package org.monjasa.interpreter;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.monjasa.interpreter.engine.interpreter.Interpreter;
import org.monjasa.interpreter.engine.parser.Parser;
import org.monjasa.interpreter.engine.lexer.Lexer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TimerTask;

public class Client {

    private static final int CELLS_IN_ROW = 9;
    private static final int CELL_SIZE = 80;
    private static final int GAP = 3;

    private JPanel mainPanel;
    private JButton executeButton;
    private JTextArea commandArea;

    private JPanel entityPanel;

    private List<JPanel> cells;
    private JLabel entityLabel;
    private int xPosition;
    private int yPosition;

    public Client() throws IOException {

        String filePath = "test/program.txt";

        try {
            commandArea.setText(new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        entityPanel.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        entityPanel.setLayout(new GridLayout(CELLS_IN_ROW, CELLS_IN_ROW, GAP, GAP));

        cells = new ArrayList<>();

        Dimension cellPreferredSize = new Dimension(CELL_SIZE, CELL_SIZE);
        BufferedImage cellImage = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResource("assets/cell.png")));

        for (int i = 0; i < CELLS_IN_ROW * CELLS_IN_ROW; i++) {
            JPanel cell = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(cellImage, 0, 0, null);
                }
            };
            cell.setPreferredSize(cellPreferredSize);

            cells.add(cell);
        }

        cells.forEach(entityPanel::add);

        BufferedImage entityImage = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResource("assets/entity.png")));
        entityLabel = new JLabel(new ImageIcon(entityImage));

        xPosition = CELLS_IN_ROW / 2;
        yPosition = CELLS_IN_ROW / 2;

        cells.get(yPosition * CELLS_IN_ROW + xPosition).add(entityLabel);

        commandArea.setMargin(new Insets(10, 10, 10, 10));

        executeButton.addActionListener(e -> execute(commandArea.getText()));
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Modest Interpreter");

        try {
            frame.setContentPane(new Client().mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void execute(String command) {

        Interpreter interpreter = new Interpreter(this, new Parser(new Lexer(command)));
        interpreter.interpret();

        /*System.out.println();
        System.out.println(interpreter.getContext().getCallStack());*/
    }

    private void updateEntityPosition(int xPositionUpdated, int yPositionUpdated) {
        cells.get(yPosition * CELLS_IN_ROW + xPosition).remove(entityLabel);
        xPosition = xPositionUpdated;
        yPosition = yPositionUpdated;
        cells.get(yPosition * CELLS_IN_ROW + xPosition).add(entityLabel);

        entityPanel.revalidate();
        entityPanel.repaint();
    }

    public void moveEntityHorizontally(int xOffset) {
        updateEntityPosition(Math.min(Math.max(xPosition + xOffset, 0), CELLS_IN_ROW - 1), yPosition);
    }

    public void moveEntityVertically(int yOffset) {
        updateEntityPosition(xPosition, Math.min(Math.max(yPosition + yOffset, 0), CELLS_IN_ROW - 1));
    }

    public void printMessage(String message) {

        Container entityCell = entityLabel.getParent();
        Point entityCoordinates = SwingUtilities.convertPoint(entityLabel, entityLabel.getX(), entityLabel.getY(), entityCell.getParent());

        JLabel messageLabel = new JLabel(message);

        try {
            InputStream fontInputStream = this.getClass().getClassLoader().getResourceAsStream("assets/fonts/gnomoria.ttf");
            Font derivedFont = Font.createFont(Font.TRUETYPE_FONT, fontInputStream).deriveFont(18f);
            messageLabel.setFont(derivedFont);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Popup popup = PopupFactory.getSharedInstance().getPopup(
                entityCell.getParent(),
                messageLabel,
                entityCoordinates.x + 10,
                entityCoordinates.y + 10
        );

        popup.show();
        new java.util.Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        popup.hide();
                    }
                },
                2000
        );
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(3, 3, new Insets(10, 10, 10, 10), -1, -1));
        Font mainPanelFont = this.$$$getFont$$$("Open Sans", -1, 12, mainPanel.getFont());
        if (mainPanelFont != null) mainPanel.setFont(mainPanelFont);
        entityPanel = new JPanel();
        entityPanel.setLayout(new BorderLayout(0, 0));
        entityPanel.setBackground(new Color(-15050988));
        entityPanel.setForeground(new Color(-15050988));
        mainPanel.add(entityPanel, new GridConstraints(0, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        mainPanel.add(scrollPane1, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        commandArea = new JTextArea();
        Font commandAreaFont = this.$$$getFont$$$("Open Sans", -1, 12, commandArea.getFont());
        if (commandAreaFont != null) commandArea.setFont(commandAreaFont);
        scrollPane1.setViewportView(commandArea);
        executeButton = new JButton();
        Font executeButtonFont = this.$$$getFont$$$("Open Sans", Font.PLAIN, 16, executeButton.getFont());
        if (executeButtonFont != null) executeButton.setFont(executeButtonFont);
        executeButton.setText("Execute");
        mainPanel.add(executeButton, new GridConstraints(1, 1, 2, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 50), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
