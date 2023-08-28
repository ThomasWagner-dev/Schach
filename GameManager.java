public class GameManager {
    public Board board;
    public Timer timer;
    public SchachGUI gui;
    public GameState gameState;
    public GameManager() {
        this.board = new Board();
        board.buildBoard(ChessColor.WHITE);

        this.timer = new Timer();
        this.gui = new SchachGUI();
        this.gui.updateSchachbrett(this.board.board);
        this.gameState = GameState.STANDBY;
        startGame();
    }

    public void startGame() {
        this.gameState = GameState.WHITE_TURN;
    }

    public void move(int y, int x, int y2, int x2) {
        if (this.gameState == GameState.WHITE_TURN) {
            this.movePiece(y, x, y2, x2);
            this.gameState = GameState.BLACK_TURN;
        } else if (this.gameState == GameState.BLACK_TURN) {
            this.movePiece(y, x, y2, x2);
            this.gameState = GameState.WHITE_TURN;
        }
        this.gui.updateSchachbrett(this.board.board);
        for (int i = 0; i < this.board.board.length; i++) {
            for (int j = 0; j < this.board.board.length; j++) {
                if (this.board.board[i][j] != null) {
                    MovesetGenerator.generateMoves(this.board, this.board.board[i][j]);
                }
            }
        }
        if (MovesetGenerator.isInCheck(this.board) == ChessColor.BLACK) {
            MovesetGenerator.removeIllegalMovesInCheck(board, ChessColor.BLACK);
        } else if (MovesetGenerator.isInCheck(this.board) == ChessColor.WHITE) {
            MovesetGenerator.removeIllegalMovesInCheck(board, ChessColor.WHITE);
        }
    }

    public void movePiece(int y, int x, int y2, int x2) {
        Piece piece = this.board.board[x][y];
        this.board.setField(y, x, null);
        this.board.setField(y2, x2, piece);
    }
}