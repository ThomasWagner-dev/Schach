import figuren.Figur;

public class Board {
    public Figur[][] feld;

    public Board(Figur[][] feld) {
        this.feld = feld;
    }

    public void buildBoard() {
        for (int i = 0; i < 8; i++) {
            setFeld(i, 1, );
        }
        for (int i = 0; i < 8; i++) {
            setFeld(i, 6, );
        }
    }

    public void setFeld(int x, int y, Figur figur) {
        this.feld[x][y] = figur;
    }
}
