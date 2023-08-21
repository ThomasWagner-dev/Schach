import figuren.Piece;
import figuren.Piececolor;
import figuren.Piecetype;

public class Board {
    public Piece[][] feld;

    public Board() {
        this.feld = new Piece[8][8];
    }

    public void buildBoard(Piececolor side) {
        Piececolor otherSide = side == Piececolor.WHITE ? Piececolor.BLACK : Piececolor.WHITE;
        // Place Pawns
        for (int i = 0; i < 8; i++) {
            this.feld[1][i] = new Piece(side, Piecetype.PAWN, i, 1);
            this.feld[6][i] = new Piece(otherSide, Piecetype.PAWN, i, 6);
        }
        // Place Rooks
        this.feld[0][0] = new Piece(side, Piecetype.ROOK, 0, 0);
        this.feld[0][7] = new Piece(side, Piecetype.ROOK, 7, 0);
        this.feld[7][0] = new Piece(otherSide, Piecetype.ROOK, 0, 7);
        this.feld[7][7] = new Piece(otherSide, Piecetype.ROOK, 7, 7);
        // Place Knights
        this.feld[0][1] = new Piece(side, Piecetype.KNIGHT, 1, 0);
        this.feld[0][6] = new Piece(side, Piecetype.KNIGHT, 6, 0);
        this.feld[7][1] = new Piece(otherSide, Piecetype.KNIGHT, 1, 7);
        this.feld[7][6] = new Piece(otherSide, Piecetype.KNIGHT, 6, 7);
        // Place Bishops
        this.feld[0][2] = new Piece(side, Piecetype.BISHOP, 2, 0);
        this.feld[0][5] = new Piece(side, Piecetype.BISHOP, 5, 0);
        this.feld[7][2] = new Piece(otherSide, Piecetype.BISHOP, 2, 7);
        this.feld[7][5] = new Piece(otherSide, Piecetype.BISHOP, 5, 7);
        // Place Queens
        this.feld[0][3] = new Piece(side, Piecetype.QUEEN, 3, 0);
        this.feld[7][3] = new Piece(otherSide, Piecetype.QUEEN, 3, 7);
        // Place Kings
        this.feld[0][4] = new Piece(side, Piecetype.KING, 4, 0);
        this.feld[7][4] = new Piece(otherSide, Piecetype.KING, 4, 7);
    }

    public void setFeld(int x, int y, Piece piece) {
        this.feld[x][y] = piece;
    }

    public Piece getPiece(int x, int y) {
        return this.feld[y][x];
    }

    public int[] getPiecePos(Piece p) {
        for (int i = 0; i < feld.length; i++) {
            for (int j= 0; j < feld.length; j++) {
                if (feld[i][j] == p) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}
