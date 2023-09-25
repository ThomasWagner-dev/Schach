import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class SchachGUI extends JFrame {
    private final int SIZE = 8;
    private final int CELL_SIZE = 80;
    private final String IMAGE_PATH = "./assets";

    private final String[] FIGUREN_BILDER = {"rook", "knight", "bishop", "king", "queen", "pawn"};

    private final JPanel schachbrettPanel;
    private final JPanel infoPanel;
    private final JLabel labelText;
    private final JButton[] buttons;

    private JButton selectedButton = null;
    List<Integer> moveClear = new ArrayList<>();
    private GameManager gameManager;
    Timer timerPanel = new Timer(this);



    public SchachGUI(GameManager gameManager) {
        this.gameManager = gameManager;
        setTitle("Schachspiel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        schachbrettPanel = new JPanel(new GridLayout(SIZE, SIZE));
        schachbrettPanel.setPreferredSize(new Dimension(CELL_SIZE * SIZE, CELL_SIZE * SIZE));
        infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(200, CELL_SIZE * SIZE));
        infoPanel.setBackground(Color.BLACK);
        labelText = new JLabel("das ist \n ein test");
        labelText.setForeground(Color.WHITE);
        labelText.setHorizontalAlignment(SwingConstants.CENTER);
        buttons = new JButton[5];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("Button" + (i + 1));
        }
        infoPanel.add(timerPanel);
        infoPanel.add(labelText);
        for (JButton button : buttons) {
            infoPanel.add(button);
        }
        initializeSchachbrett();
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
        if (button == null) {
            return;
        }
        String positionFrom = button.getName();
        String[] fromParts = positionFrom.split(",");
        int x = Integer.parseInt(fromParts[1]);
        int y = Integer.parseInt(fromParts[0]);
        System.out.println("Piece moved from: " + positionFrom + " to: " + positionFrom);
        moveClear.add(y);
        moveClear.add(x);

        if (moveClear.size() == 4) {
            gameManager.move(moveClear.get(0), moveClear.get(1), moveClear.get(2), moveClear.get(3));
            updateSchachbrett(gameManager.board.board);
            if(timerPanel.isTimer1Active) {
                timerPanel.startTimer2();
            }
            else {
                timerPanel.startTimer1();
            }
            System.out.println("clear");
            moveClear.clear();
        } else {
            highlightPossibleMoves(gameManager.board.board[y][x]);
        }

    }



    private void highlightPossibleMoves(Piece piece) {
        if(gameManager.isGameFinsished() != null){
            return;
        }
        for (int[] move :piece.moves) {
            int newX = piece.posX + move[1];
            int newY = piece.posY + move[0];
            JButton targetButton = getButtonAtPosition(newX, newY);

            if (targetButton == null) {
                return;
            }

            if (gameManager.board.board[newY][newX] == null) {
                String bildDatei = "blueField.png";
                String imagePath = IMAGE_PATH + File.separator + bildDatei;
                try {
                    BufferedImage originalImage = ImageIO.read(new File(imagePath));
                    Image scaledImage = originalImage.getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(scaledImage);

                    targetButton.setIcon(imageIcon);
                    targetButton.setContentAreaFilled(false);
                    targetButton.setBorderPainted(false);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Error loading image: " + bildDatei);
                }

            } else {
                String bildDatei = getHighlightImg(gameManager.board.board[newY][newX]);
                String imagePath = IMAGE_PATH + File.separator + bildDatei;
                try {
                    BufferedImage originalImage = ImageIO.read(new File(imagePath));
                    Image scaledImage = originalImage.getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(scaledImage);

                    targetButton.setIcon(imageIcon);
                    targetButton.setContentAreaFilled(false);
                    targetButton.setBorderPainted(false);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Error loading image: " + bildDatei);
                }
            }
        }
        }


    private String getHighlightImg(Piece piece ){
        String bildDatei = "";
        switch (piece.piecetype){

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
        bildDatei += " " + (piece.color == ChessColor.WHITE ? "w" : "b");
        bildDatei += " " + ("h.png");

        return bildDatei;
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

    private JButton getButtonAtPosition(int x, int y) {
        Component[] components = schachbrettPanel.getComponents();

        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                String[] positionParts = button.getName().split(",");
                int buttonX = Integer.parseInt(positionParts[1]);
                int buttonY = Integer.parseInt(positionParts[0]);

                if (buttonX == x && buttonY == y) {
                    return button;
                }
            }
        }

        return null;
    }

    private Color getCellColor(int row, int col) {
        return (row + col) % 2 == 0 ? new Color(245, 245, 220) : new Color(0, 0, 0);
    }

    public String showWinner(ChessColor gameFinsished) {
        if(gameFinsished == ChessColor.BLACK){
            labelText.setText("Schwarz hat Gewonnen");
        }
        if(gameFinsished == ChessColor.WHITE){
            labelText.setText("Weiß hat Gewonnen");
        }
        return labelText.getText ();
    }
}
