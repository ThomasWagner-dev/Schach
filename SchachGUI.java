import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SchachGUI extends JFrame {
    private final int SIZE = 8;
    private final int CELL_SIZE = 80;
    private final String IMAGE_PATH = "./assets";

    private final String[] FIGUREN_BILDER = {"rook", "knight", "bishop", "king", "queen", "pawn"};

    private final JPanel schachbrettPanel;
    private final JPanel infoPanel;

    private JButton selectedButton = null;
  private  GameManager gameManager;

    public SchachGUI(GameManager gameManager) {
        this.gameManager = gameManager;
        setTitle("Schachspiel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        schachbrettPanel = new JPanel(new GridLayout(SIZE, SIZE));
        schachbrettPanel.setPreferredSize(new Dimension(CELL_SIZE * SIZE, CELL_SIZE * SIZE));

        initializeSchachbrett();

        infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(200, CELL_SIZE * SIZE));
        infoPanel.setBackground(Color.BLACK);

        setLayout(new BorderLayout());
        add(schachbrettPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);

        initializeRowNumbers();
        initializeColumnLetters();
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


    public void updateSchachbrett(Piece[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JButton button = (JButton) schachbrettPanel.getComponent(row * SIZE + col);
                button.removeAll();

                String bildDatei = getBildDatei(board, row, col);
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


    private void initializeRowNumbers() {
        JPanel rowNumbersPanel = new JPanel(new GridLayout(SIZE, 1));
        for (int row = SIZE; row >= 1; row--) {
            JLabel label = new JLabel(String.valueOf(row), SwingConstants.CENTER);
            rowNumbersPanel.add(label);
        }
        add(rowNumbersPanel, BorderLayout.WEST);
    }


    private void initializeColumnLetters() {
        JPanel columnLettersPanel = new JPanel(new GridLayout(1, SIZE));
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        for (int col = 0; col < SIZE; col++) {
            JLabel label = new JLabel(String.valueOf(letters[col]), SwingConstants.CENTER);
            columnLettersPanel.add(label);
        }
        columnLettersPanel.add(new JLabel(""));
        columnLettersPanel.add(new JLabel(""));
        columnLettersPanel.add(new JLabel(""));

        add(columnLettersPanel, BorderLayout.NORTH);
    }

    private String getBildDatei(Piece[][] board, int y, int x) {
        String bildDatei = "";
        if (board[y][x] != null) {
            switch (board[y][x].piecetype) {
                case ROOK:
                    bildDatei = FIGUREN_BILDER[0];
                break;
                case KNIGHT:
                    bildDatei = FIGUREN_BILDER[1];
                    break;
                case BISHOP:
                    bildDatei = FIGUREN_BILDER[2];
                    break;
                case KING:
                    bildDatei = FIGUREN_BILDER[3];
                    break;
                case QUEEN:
                    bildDatei = FIGUREN_BILDER[4];
                    break;
                case PAWN:
                    bildDatei = FIGUREN_BILDER[5];
                    break;
            }
            bildDatei += " " + (board[y][x].color == ChessColor.WHITE ? "w" : "b");
            bildDatei += " " + ((y + x) % 2 == 0 ? "w.png" : "b.png");
        } else {
            bildDatei = (y + x) % 2 == 0 ? "whiteField.png" : "blackField.png";
        }
        return bildDatei;
    }

 private void handleCellClick(JButton button) {
        if (selectedButton == null) {
            selectedButton = button;
            button.setBackground(Color.YELLOW);
        } else {
            String positionFrom = selectedButton.getName();
            String positionTo = button.getName();
            String[] fromParts = positionFrom.split(",");
            int x = Integer.parseInt(fromParts[0]);
            int x1 = Integer.parseInt(fromParts[1]);
            String[] toParts = positionTo.split(",");
            int y = Integer.parseInt(toParts[0]);
            int y1 = Integer.parseInt(toParts[1]);
            System.out.println("Piece moved from: " + positionFrom + " to: " + positionTo);
            gameManager.move(y,x,y1,x1);
        }
    }


    private Color getCellColor(int row, int col) {
        return (row + col) % 2 == 0 ? new Color(245, 245, 220) : new Color(0, 0, 0);
    }
}
