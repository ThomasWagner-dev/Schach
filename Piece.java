import java.util.ArrayList;

public class Piece {



    public int posX;
    public int posY;
    public int value;
    public Piecetype piecetype;
    public boolean moved;
    public ArrayList<int[]> moves;
    public ChessColor color; //0 = white, 1 = black
//{1, 0}, {1,1},{0,1},{-1,1},,{-1,-1},{0,-1},{1,-1}{2,0},{-2,0}
    public Piece(ChessColor iChessColor, Piecetype iPiecetype, int posY, int posX ) {
        this.moves = new ArrayList<>();
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
        this.color = iChessColor;
    }


    public boolean canAttack(int y, int x) {
        for (int[] move : this.moves) {
            if (this.posX + move[1] == x && this.posY + move[0] == y) {
                return true;
            }
        }
        return false;
    }
}

