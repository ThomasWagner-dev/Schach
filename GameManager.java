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
        System.out.println("X: "+ x + " X2: " + x2  + " Y: " + y + " Y2: " + y2);
        if (this.board.board[y][x] == null) {
            return;
        }
        /*if(board.getPiece(y, x).piecetype == Piecetype.KING){
            if(x2-x==-2){
                if(board.getPiece(y, x).color== ChessColor.WHITE) {
                    move(0, 0, 0, 3);
                }else{
                    move(7, 0, 0, 3);
                }
            }else if(x2-x==2){
                if(board.getPiece(y, x).color== ChessColor.WHITE) {
                    move(0, 0, 0, -2);
                }else{
                    move(7, 0, 0, -2);
                }
            }
        }*/

        if (this.gameState == GameState.WHITE_TURN) {
            if (this.board.board[y][x].color == ChessColor.BLACK) {
                return;
            }
            if (!isValidMove(y, x, y2, x2)) {
                return;
            }
            this.movePiece(y, x, y2, x2);
            this.gameState = GameState.BLACK_TURN;
        } else if (this.gameState == GameState.BLACK_TURN) {
            if (this.board.board[y][x].color == ChessColor.WHITE) {
                return;
            }
            if (!isValidMove(y, x, y2, x2)) {
                return;
            }
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
            if (move[0] == y - y2 && move[1] == x - x2) {
                return true;
            }
        }
        return false;
    }
}
