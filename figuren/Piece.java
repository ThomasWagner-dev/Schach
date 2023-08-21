package figuren;
public class Piece {
    public enum piecetype {
        KING,
        QUEEN,
        PAWN,
        KNIGHT,
        BISHOP,
        ROOK

    }

    public int value;
    public piecetype piecetype;
    public boolean moved;
    public int[][] moves;
    public int color; //0 = white, 1 = black
//{1, 0}, {1,1},{0,1},{-1,1},,{-1,-1},{0,-1},{1,-1}{2,0},{-2,0}
    public Piece(int iColor, piecetype iPiecetype) {
        this.piecetype = iPiecetype;
        this.moved = false;
        switch (iPiecetype) {
            case KING:
                this.value = 99999999;
                this.moves= new int[][]{{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-2,0},{2,0}};
                break;
            case QUEEN:
                this.value = 9;
                break;
            case PAWN:
                this.value = 1;
                break;
            case KNIGHT:
                this.value = 3;
                break;
            case BISHOP:
                this.value = 3;
                break;
            case ROOK:
                this.value = 5;
                break;
        }
        this.color = iColor;
    }


}

