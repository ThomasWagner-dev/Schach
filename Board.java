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
}
