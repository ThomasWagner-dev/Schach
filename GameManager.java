import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDate;

public class GameManager {
    public Board board;
    public SchachGUI gui;
    public GameState gameState;
    public MgrLog log = new MgrLog ();
    public int movesZaehler = 0;
    public GameManager() {
        this.board = new Board();
        board.buildBoard(ChessColor.WHITE);

        this.gui = new SchachGUI(this);
        this.gui.updateSchachbrett(this.board.board);
        this.gameState = GameState.STANDBY;
        log.init("Offline Chess","Lokal", LocalDate.now(),"-","PlayerOne","PlayerTwo",
                0,0,"120");
        startGame();
    }
    public void logMove(int y, int x, int y2, int x2){
        System.out.println("Y: "+ y + " X: " + x  + " Y2: " + y2 + " X2: " + x2);
        if (board.board[y2][x2].color == ChessColor.WHITE)
            movesZaehler ++;

        String timerString;
        if(gui.timerPanel.isTimer1Active) timerString = gui.timerPanel.timerLabel1.getText ();
        else timerString = gui.timerPanel.timerLabel2.getText ();
        log.addMove(movesZaehler, y2,x2,timerString);
    }
    public void startGame() {
        this.gameState = GameState.WHITE_TURN;
    }

    public void move(int y, int x, int y2, int x2) {
        this.board.lastMove = new int[]{y, x, y2, x2};
        if (this.board.board[y][x] == null) {
            return;
        }
        for (int[] move : this.board.board[y][x].moves) {
            System.out.println("Move: " + move[0] + " " + move[1]);
        }
        if (!isValidMove(y, x, y2, x2)) {
            return;
        }
        if(!this.doSpecialMove(y, x, y2, x2)){
            this.movePiece(y, x, y2, x2);
        }
        logMove(y,x,y2,x2);
        if(gui.timerPanel.isTimer1Active) {
            gui.timerPanel.startTimer2();
        }
        else {
            gui.timerPanel.startTimer1();
        }
        this.gameState = this.gameState==GameState.WHITE_TURN?GameState.BLACK_TURN:GameState.WHITE_TURN;
        if (this.isGameFinsished() != null) {
            this.gameState = GameState.FINISHED;
            this.gui.updateSchachbrett(this.board.board);
            this.gui.showWinner(this.isGameFinsished());
            return;
        }
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

    private boolean doSpecialMove(int y, int x, int y2, int x2) {
        Piece piece = this.board.board[y][x];
        if (piece.piecetype == Piecetype.PAWN) {
            if (piece.color == ChessColor.WHITE && y2 == 7) {
                this.board.board[y][x] = new Piece(ChessColor.WHITE, Piecetype.QUEEN, y, x);
                return true;
            } else if (piece.color == ChessColor.BLACK && y2 == 0) {
                this.board.board[y][x] = new Piece(ChessColor.BLACK, Piecetype.QUEEN, y, x);
                return true;
            }
            int vorzeichen = -1;
            if (piece.color == ChessColor.BLACK) {
                vorzeichen = 1;
            }
            if (x != x2) {
                if (this.board.board[y2][x2] == null){
                    this.board.board[y2 + vorzeichen][x2] = null;
                }
            }
        }
        // Check for castling
        if (piece.piecetype == Piecetype.KING) {
            if (piece.color == ChessColor.WHITE && y == 0 && x == 4 && y2 == 0 && x2 == 6) {
                this.movePiece(0, 7, 0, 5);
                piece.moved = true;
                this.board.board[0][6] = this.board.board[0][4];
                this.board.board[0][4] = null;
                return true;
            } else if (piece.color == ChessColor.WHITE && y == 0 && x == 4 && y2 == 0 && x2 == 2) {
                this.movePiece(0, 0, 0, 3);
                piece.moved = true;
                this.board.board[0][2] = this.board.board[0][4];
                this.board.board[0][4] = null;
                return true;
            } else if (piece.color == ChessColor.BLACK && y == 7 && x == 4 && y2 == 7 && x2 == 6) {
                this.movePiece(7, 7, 7, 5);
                piece.moved = true;
                this.board.board[7][5] = this.board.board[7][7];
                this.board.board[7][7] = null;
                return true;
            } else if (piece.color == ChessColor.BLACK && y == 7 && x == 4 && y2 == 7 && x2 == 2) {
                this.movePiece(7, 0, 7, 3);
                piece.moved = true;
                this.board.board[7][2] = this.board.board[7][4];
                this.board.board[7][4] = null;
                return true;
            }
        }
        return false;
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

    public ChessColor isGameFinsished() {
        ArrayList<Piece> whitePieces = new ArrayList<>();
        ArrayList<Piece> blackPieces = new ArrayList<>();
        for (Piece[] row : this.board.board) {
            for (Piece piece : row) {
                if (piece != null && piece.color == ChessColor.WHITE) {
                    whitePieces.add(piece);
                }
            }
        }
        for (Piece[] row : this.board.board) {
            for (Piece piece : row) {
                if (piece != null && piece.color == ChessColor.BLACK) {
                    blackPieces.add(piece);
                }
            }
        }
        boolean whiteLost = true;
        for (Piece piece: whitePieces) {
            if(!piece.moves.isEmpty()){
                whiteLost = false;
            }
        }
        boolean blackLost = true;
        for (Piece piece: blackPieces) {
            if(!piece.moves.isEmpty()){
                blackLost = false;
            }
        }
        if(whiteLost){
            return ChessColor.BLACK;
        } else if(blackLost){
            return ChessColor.WHITE;
        } else {
            return null;
        }
    }
}
