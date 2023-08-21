import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Gui extends JFrame {
    private final int SIZE = 8;
    private final int CELL_SIZE = 80;
    private final String IMAGE_PATH = "C:\\Java-Projekte\\SchachFrontend\\imgs";

    private final String[] WEISS_FIGUREN_BILDER = {
            "turm_w.png", "sprienger_w.png", "laeufer_w.png", "koenig_w.png",
            "dame_w.png", "laeufer_w.png", "sprienger_w.png", "turm_w.png"
    };

    private final String[] SCHWARZ_FIGUREN_BILDER = {
            "turm_s.png", "sprienger_s.png", "laeufer_s.png", "dame_s.png",
            "koenig_s.png", "laeufer_s.png", "sprienger_s.png", "turm_s.png"
    };
    private final String BAUERN_BILD_W = "bauer_w.png";
    private final String BAUERN_BILD_S = "bauer_s.png";
    private final JPanel schachbrettPanel;
    private final JPanel infoPanel;

    public Gui() {
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
                if (row == SIZE - 8 || row == SIZE) {
                    drawFigur(panel, WEISS_FIGUREN_BILDER[col - 1]);
                } else if (row == 1 || row == 1) {
                    drawFigur(panel, SCHWARZ_FIGUREN_BILDER[col - 1]);
                } else if (row == SIZE -7  || row == SIZE - 1) {
                    drawFigur(panel, BAUERN_BILD_W);
                } else if (row == 2 || row == SIZE - 1) {
                    drawFigur(panel, BAUERN_BILD_S);
                } else {
                    drawFigur(panel, "");
                }
                panel.revalidate();
                panel.repaint();
            }
        }
    }

    private BufferedImage removeBackground(BufferedImage originalImage, Color backgroundColor) {
        BufferedImage newImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < originalImage.getHeight(); y++) {
            for (int x = 0; x < originalImage.getWidth(); x++) {
                Color pixelColor = new Color(originalImage.getRGB(x, y), true);

                if (!pixelColor.equals(backgroundColor)) {
                    newImage.setRGB(x, y, pixelColor.getRGB());
                }
            }
        }

        return newImage;
    }

    private void drawFigur(JPanel panel, String bildDatei) {
        if (!bildDatei.isEmpty()) {
            String imagePath = IMAGE_PATH + "\\" + bildDatei;
            System.out.println("Loading image: " + imagePath);

            try {
                BufferedImage originalImage = ImageIO.read(new File(imagePath));
                System.out.println("Image loaded successfully.");

                Image scaledImage = originalImage.getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH);
                JLabel label = new JLabel(new ImageIcon(scaledImage));
                panel.add(label);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error loading image: " + bildDatei);
            }
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
        SwingUtilities.invokeLater(Gui::new);
    }
}
