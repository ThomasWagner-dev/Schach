package figuren;
public class Piece {



    public int posX;
    public int posY;
    public int value;
    public Piecetype piecetype;
    public boolean moved;
    public int[][] moves;
    public Piececolor color; //0 = white, 1 = black
//{1, 0}, {1,1},{0,1},{-1,1},,{-1,-1},{0,-1},{1,-1}{2,0},{-2,0}
    public Piece(Piececolor iColor, Piecetype iPiecetype, int posX, int posY ) {
        this.piecetype = iPiecetype;
        this.moved = false;
        this.posX = posX;
        this.posY = posY;
        switch (this.piecetype) {
            case KING:
                this.value = 99999999;
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

