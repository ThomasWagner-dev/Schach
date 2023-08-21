import java.util.ArrayList;

public class Board {
    public Piece[][] board;

    public Board() {
        this.board = new Piece[8][8];
    }

    public void buildBoard(Color side) {
        Color otherSide = side == Color.WHITE ? Color.BLACK : Color.WHITE;
        // Place Pawns
        for (int i = 0; i < 8; i++) {
            this.board[1][i] = new Piece(side, Piecetype.PAWN, i, 1);
            this.board[6][i] = new Piece(otherSide, Piecetype.PAWN, i, 6);
        }
        // Place Rooks
        this.board[0][0] = new Piece(side, Piecetype.ROOK, 0, 0);
        this.board[0][7] = new Piece(side, Piecetype.ROOK, 7, 0);
        this.board[7][0] = new Piece(otherSide, Piecetype.ROOK, 0, 7);
        this.board[7][7] = new Piece(otherSide, Piecetype.ROOK, 7, 7);
        // Place Knights
        this.board[0][1] = new Piece(side, Piecetype.KNIGHT, 1, 0);
        this.board[0][6] = new Piece(side, Piecetype.KNIGHT, 6, 0);
        this.board[7][1] = new Piece(otherSide, Piecetype.KNIGHT, 1, 7);
        this.board[7][6] = new Piece(otherSide, Piecetype.KNIGHT, 6, 7);
        // Place Bishops
        this.board[0][2] = new Piece(side, Piecetype.BISHOP, 2, 0);
        this.board[0][5] = new Piece(side, Piecetype.BISHOP, 5, 0);
        this.board[7][2] = new Piece(otherSide, Piecetype.BISHOP, 2, 7);
        this.board[7][5] = new Piece(otherSide, Piecetype.BISHOP, 5, 7);
        // Place Queens
        this.board[0][3] = new Piece(side, Piecetype.QUEEN, 3, 0);
        this.board[7][3] = new Piece(otherSide, Piecetype.QUEEN, 3, 7);
        // Place Kings
        this.board[0][4] = new Piece(side, Piecetype.KING, 4, 0);
        this.board[7][4] = new Piece(otherSide, Piecetype.KING, 4, 7);
    }

    public void setField(int x, int y, Piece piece) {
        this.board[x][y] = piece;
    }

    public Piece getPiece(int x, int y) {
        return this.board[y][x];
    }

    public int[] getPiecePos(Piece p) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == p) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public boolean isFieldAttackedBy(Color color, int x, int y) {
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
            if (piece.canAttack(this, x, y)) {
                return true;
            }
        }
        return false;
    }
}
