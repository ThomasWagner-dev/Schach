import javax.swing.*;
import java.awt.*;

public class SchachGUI extends JFrame {
    private final int SIZE = 8;
    private final int CELL_SIZE = 80;
    private final String[] FIGUREN_BILDER = {
            "rook.png", "knight.png", "bishop.png", "queen.png",
            "king.png", "bishop.png", "knight.png", "rook.png"
    };
    private final String BAUERN_BILD = "pawn.png";

    private final JPanel schachbrettPanel;
    private final JPanel infoPanel;

    public SchachGUI() {
        setTitle("Schachspiel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        schachbrettPanel = new JPanel(new GridLayout(SIZE + 1, SIZE + 1));
        schachbrettPanel.setPreferredSize(new Dimension((SIZE + 2) * CELL_SIZE, (SIZE + 2) * CELL_SIZE));

        initializeSchachbrett();
        drawSchachbrett();

        infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(200, (SIZE + 1) * CELL_SIZE));
        infoPanel.setBackground(Color.BLACK);

        setLayout(new BorderLayout());
        add(schachbrettPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                adjustLayout();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void adjustLayout() {
        int windowHeight = getHeight();
        int cellSize = Math.min(windowHeight / (SIZE + 2), 200);
        schachbrettPanel.setPreferredSize(new Dimension((SIZE + 2) * cellSize, (SIZE + 2) * cellSize));

        for (Component component : schachbrettPanel.getComponents()) {
            if (component instanceof JPanel) {
                ((JPanel) component).setPreferredSize(new Dimension(cellSize, cellSize));
            }
        }

        revalidate();
        repaint();
    }



    private void initializeSchachbrett() {
        int cellSize = schachbrettPanel.getPreferredSize().width / (SIZE + 2);

        for (int col = 0; col <= SIZE; col++) {
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(cellSize, cellSize));
            panel.setBackground(Color.WHITE);
            JLabel label = new JLabel(String.valueOf(col));
            panel.add(label);
            schachbrettPanel.add(panel);
        }

        for (int row = 0; row < SIZE; row++) {
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(cellSize, cellSize));
            panel.setBackground(Color.WHITE);
            char letter = (char) ('A' + row);
            JLabel label = new JLabel(String.valueOf(letter));
            panel.add(label);
            schachbrettPanel.add(panel);

            for (int col = 0; col < SIZE; col++) {
                panel = new JPanel();
                panel.setPreferredSize(new Dimension(cellSize, cellSize));
                panel.setBackground(getCellColor(row, col));
                schachbrettPanel.add(panel);
            }
        }
    }

    private void drawSchachbrett() {
        for (int row = 1; row <= SIZE; row++) {
            for (int col = 1; col <= SIZE; col++) {
                JPanel panel = (JPanel) schachbrettPanel.getComponent(row * (SIZE + 1) + col);
                panel.removeAll();

                if (row == 1 || row == SIZE) {
                    drawFigur(panel, FIGUREN_BILDER[col - 1]);
                } else if (row == 2 || row == SIZE - 1) {
                    drawFigur(panel, BAUERN_BILD);
                } else {
                    drawFigur(panel, "");
                }
                panel.revalidate();
                panel.repaint();
            }
        }
    }

    private void drawFigur(JPanel panel, String bildDatei) {
        if (!bildDatei.isEmpty()) {
            ImageIcon icon = new ImageIcon(bildDatei);
            Image image = icon.getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(image));
            panel.add(label);
        }
    }

    private Color getCellColor(int row, int col) {
        if ((row + col) % 2 == 0) {
            return new Color(139, 69, 19);
        } else {
            return new Color(245, 245, 220);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SchachGUI::new);
    }
}
