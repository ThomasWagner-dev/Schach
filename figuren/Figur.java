package figuren;

public const enum piecetype{
    KING
    QUEEN
    PAWN
    KNIGHT
    BISHOP
    ROOK
    
}

public class Figur {
    public int value;
    public int figurtype;
    public boolean moved;
    public int[][] moves;
    public int color; //0 = white, 1 = black

    public Figur(color, figurtype){
        switch(figurtype){
            case KING:
                value = 9999999999999;
                break;
            case QUEEN:
                value = 9;
            break;
            case PAWN:
            value = 1;
            break;
            case KNIGHT:
            value = 3;
            break;
            case BISHOP:
            value = 3;
            break;
            case ROOK:
            value = 5;
            break
        }
    }

    pulbic moves loadMoves(){
        int[][] moves;

        return moves 
    }
}

