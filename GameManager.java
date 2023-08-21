public class GameManager {
    public Board board;
    public Timer timer;
    public SchachGUI gui;
    public GameState gameState;

    public GameManager() {
        this.board = new Board();
        board.buildBoard();
        this.timer = new Timer();
        this.gui = new SchachGUI();
        this.gameState = GameState.STANDBY;
    }

    public void startGame() {
        this.gameState = GameState.WHITE_TURN;
    }

    public void move(int x, int y, int x2, int y2) {
        if (this.gameState == GameState.WHITE_TURN) {
            this.movePiece(x, y, x2, y2);
            this.gameState = GameState.BLACK_TURN;
        } else if (this.gameState == GameState.BLACK_TURN) {
            this.movePiece(x, y, x2, y2);
            this.gameState = GameState.WHITE_TURN;
        }
    }

    public void movePiece(int x, int y, int x2, int y2) {
    }
}