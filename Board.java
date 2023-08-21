import figuren.Piece;

public class Board {
    public Piece[][] feld;

    public Board() {
        this.feld = new Piece[8][8];
    }

    public Board(Piece[][] feld) {
        this.feld = feld;
    }

    public void buildBoard() {
        for (int i = 0; i < 8; i++) {
        }
        for (int i = 0; i < 8; i++) {
        }
    }

    public void setFeld(int x, int y, Piece piece) {
        this.feld[x][y] = piece;
    }

    public Piece getFeld(int x, int y) {
        return this.feld[x][y];
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
