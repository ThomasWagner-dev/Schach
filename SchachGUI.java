import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SchachGUI extends JFrame {
    private final int SIZE = 8;
    private final int CELL_SIZE = 80;
    private final String IMAGE_PATH = "C:\\Java-Projekte\\SchachFrontend\\imgs";

    private final String[] WEISS_FIGUREN_BILDER = {
            "turm_w.png", "sprienger_w.png", "laeufer_w.png", "koenig_w.png",
            "dame_w.png", "laeufer_w.png", "sprienger_w.png", "turm_w.png"
    };

    private final String[] SCHWARZ_FIGUREN_BILDER = {
            "turm_s.png", "sprienger_s.png", "laeufer_s.png", "koenig_s.png",
            "dame_s.png", "laeufer_s.png", "sprienger_s.png", "turm_s.png"
    };
    private final String BAUERN_BILD_W = "bauer_w.png";
    private final String BAUERN_BILD_S = "bauer_s.png";
    private final JPanel schachbrettPanel;
    private final JPanel infoPanel;

    private JButton selectedButton = null;
    private Color originalButtonColor;

    public SchachGUI() {
        setTitle("Schachspiel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        schachbrettPanel = new JPanel(new GridLayout(SIZE, SIZE));
        schachbrettPanel.setPreferredSize(new Dimension(CELL_SIZE * SIZE, CELL_SIZE * SIZE));

        initializeSchachbrett();
        updateSchachbrett();

        infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(200, CELL_SIZE * SIZE));
        infoPanel.setBackground(Color.BLACK);

        setLayout(new BorderLayout());
        add(schachbrettPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeSchachbrett() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                button.setBackground(getCellColor(row, col));
                button.setName(row + "," + col);

                button.addActionListener(e -> handleCellClick(button));

                schachbrettPanel.add(button);
            }
        }
    }

    private void updateSchachbrett() {
        String[][] schachbrett = {
                {"turm_w.png", "sprienger_w.png", "laeufer_w.png", "dame_w.png", "koenig_w.png", "laeufer_w.png", "sprienger_w.png", "turm_w.png"},
                {"bauer_w.png", "bauer_w.png", "bauer_w.png", "bauer_w.png", "bauer_w.png", "bauer_w.png", "bauer_w.png", "bauer_w.png"},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"bauer_s.png", "bauer_s.png", "bauer_s.png", "bauer_s.png", "bauer_s.png", "bauer_s.png", "bauer_s.png", "bauer_s.png"},
                {"turm_s.png", "sprienger_s.png", "laeufer_s.png", "dame_s.png", "koenig_s.png", "laeufer_s.png", "sprienger_s.png", "turm_s.png"}
        };

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JButton button = (JButton) schachbrettPanel.getComponent(row * SIZE + col);
                button.removeAll();

                String bildDatei = schachbrett[row][col];
                if (!bildDatei.isEmpty()) {
                    String imagePath = IMAGE_PATH + File.separator + bildDatei;
                    try {
                        BufferedImage originalImage = ImageIO.read(new File(imagePath));
                        Image scaledImage = originalImage.getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH);
                        ImageIcon imageIcon = new ImageIcon(scaledImage);

                        button.setIcon(imageIcon);
                        button.setContentAreaFilled(false);
                        button.setBorderPainted(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.err.println("Error loading image: " + bildDatei);
                    }
                }

                button.revalidate();
                button.repaint();
            }
        }
    }

    private void handleCellClick(JButton button) {
        if (selectedButton == null) {
            selectedButton = button;
            originalButtonColor = button.getBackground();
            button.setBackground(Color.YELLOW);
        } else {
            button.setIcon(selectedButton.getIcon());
            selectedButton.setIcon(null);
            selectedButton.setBackground(originalButtonColor);
            selectedButton = null;
        }
    }

    private Color getCellColor(int row, int col) {
        return (row + col) % 2 == 0 ? new Color(245, 245, 220) : new Color(139, 69, 19);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SchachGUI::new);
    }
}