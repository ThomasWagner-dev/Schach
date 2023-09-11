public class GameManager {
    public Board board;
    public Timer timer;
    public SchachGUI gui;
    public GameState gameState;
    public GameManager() {
        this.board = new Board();
        board.buildBoard(ChessColor.WHITE);

        this.timer = new Timer();
        this.gui = new SchachGUI(this);
        this.gui.updateSchachbrett(this.board.board);
        this.gameState = GameState.STANDBY;
        startGame();
    }

    public void startGame() {
        this.gameState = GameState.WHITE_TURN;
    }

    public void move(int y, int x, int y2, int x2) {
        System.out.println("Y: "+ y + " X: " + x  + " Y2: " + y2 + " X2: " + x2);
        if (this.board.board[y][x] == null) {
            return;
        }
        for (int[] move : this.board.board[y][x].moves) {
            System.out.println("Move: " + move[0] + " " + move[1]);
        }
        if (!isValidMove(y, x, y2, x2)) {
            return;
        }
        this.movePiece(y, x, y2, x2);
        this.gameState = this.gameState==GameState.WHITE_TURN?GameState.BLACK_TURN:GameState.WHITE_TURN;

        this.gui.updateSchachbrett(this.board.board);
        for (int i = 0; i < this.board.board.length; i++) {
            for (int j = 0; j < this.board.board.length; j++) {
                if (this.board.board[i][j] != null) {
                    MovesetGenerator.generateMoves(this.board, this.board.board[i][j]);
                }
            }
        }
        MovesetGenerator.removeIllegalMovesInCheck(board, MovesetGenerator.isInCheck(this.board));
    }

    public void movePiece(int y, int x, int y2, int x2) {
        Piece piece = this.board.board[y][x];
        this.board.setField(y, x, null);
        this.board.setField(y2, x2, piece);
        piece.moved = true;
        piece.posY = y2;
        piece.posX = x2;
    }

    public boolean isValidMove(int y, int x, int y2, int x2) {
        Piece piece = this.board.board[y][x];
        if (piece == null) {
            return false;
        }
        if (piece.color != (this.gameState == GameState.WHITE_TURN?ChessColor.WHITE:ChessColor.BLACK)) {
            return false;
        }
        for (int i = 0; i < piece.moves.size(); i++) {
            int[] move = piece.moves.get(i);
            if (move[0] == y2 - y && move[1] == x2 - x) {
                return true;
            }
        }
        return false;
    }
}
