import figuren.Piece;

public class MovesetGenerator {
    private Board field;
    public void generateMoves(Piece piece, Board field){
        this.field = field;
        switch (piece.piecetype) {
            case KING:

                break;
            case QUEEN:
                break;
            case PAWN:
                break;
            case KNIGHT:
                break;
            case BISHOP:
                break;
            case ROOK:
                break;
        }
    }

    private void generateKingMoves(Piece piece){
        int[][] theoreticalMoves = new int[][]{{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-2,0},{2,0}};
        //check if rook is on A1/A8 if white/black
        if(piece.color =  && field.feld[0][0].piecetype=ROOK){

        }
    }

    public int[][] generatePawnMoves(Piece piece, Board feld){
        //
        int[][] moves = new int[4][2];
        return moves;
    }
}
