import java.util.ArrayList;

public class Board {
    public Piece[][] board;

    public Board() {
        this.board = new Piece[8][8];
    }

    public void buildBoard(ChessColor side) {
        ChessColor otherSide = side == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE;
        // Place Pawns
        for (int i = 0; i < 8; i++) {
            this.board[1][i] = new Piece(side, Piecetype.PAWN, 1, i);
            this.board[6][i] = new Piece(otherSide, Piecetype.PAWN, 6, i);
        }
        // Place Rooks
        this.board[0][0] = new Piece(side, Piecetype.ROOK, 0, 0);
        this.board[0][7] = new Piece(side, Piecetype.ROOK, 0, 7);
        this.board[7][0] = new Piece(otherSide, Piecetype.ROOK, 7, 0);
        this.board[7][7] = new Piece(otherSide, Piecetype.ROOK, 7, 7);
        // Place Knights
//        this.board[0][1] = new Piece(side, Piecetype.KNIGHT, 0, 1);
//        this.board[0][6] = new Piece(side, Piecetype.KNIGHT, 0, 6);
//        this.board[7][1] = new Piece(otherSide, Piecetype.KNIGHT, 7, 1);
//        this.board[7][6] = new Piece(otherSide, Piecetype.KNIGHT, 7, 6);
        // Place Bishops
//        this.board[0][2] = new Piece(side, Piecetype.BISHOP, 0, 2);
//        this.board[0][5] = new Piece(side, Piecetype.BISHOP, 0, 5);
//        this.board[7][2] = new Piece(otherSide, Piecetype.BISHOP, 7, 2);
//        this.board[7][5] = new Piece(otherSide, Piecetype.BISHOP, 7, 5);
        // Place Queens
//        this.board[0][3] = new Piece(side, Piecetype.QUEEN, 0, 3);
//        this.board[7][3] = new Piece(otherSide, Piecetype.QUEEN, 7, 3);
        // Place Kings
        this.board[0][4] = new Piece(side, Piecetype.KING, 0, 4);
        this.board[7][4] = new Piece(otherSide, Piecetype.KING, 7, 4);

        // Collect Pieces
        ArrayList<Piece> pieces = new ArrayList<>();
        for (Piece[] row : this.board) {
            for (Piece piece : row) {
                if (piece != null) {
                    pieces.add(piece);
                }
            }
        }
        // Generate Movesets
        for (Piece piece : pieces) {
            MovesetGenerator.generateMoves(this, piece);
        }
    }

    public void setField(int y, int x, Piece piece) {
        this.board[y][x] = piece;
    }

    public Piece getPiece(int y, int x) {
        return this.board[y][x];
    }

    public int[] getPiecePos(Piece p) {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.length; x++) {
                if (board[y][x] == p) {
                    return new int[]{y, x};
                }
            }
        }
        return null;
    }

    public boolean isFieldAttackedBy(ChessColor color, int y, int x) {
        // Collect Pieces
        ArrayList<Piece> pieces = new ArrayList<>();
        for (Piece[] row : this.board) {
            for (Piece piece : row) {
                if (piece != null && piece.color == color) {
                    pieces.add(piece);
                }
            }
        }
        // Check if any piece can attack the field
        for (Piece piece : pieces) {
            if (piece.canAttack(y, x)) {
                return true;
            }
        }
        return false;
    }
}
