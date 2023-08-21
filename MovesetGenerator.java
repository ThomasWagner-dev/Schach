import java.util.ArrayList;
import java.util.Collections;
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
        ArrayList<int[]> theoreticalMoves = new ArrayList<>();
        int[][] temp = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-2,0},{2,0}};
        Collections.addAll(theoreticalMoves, temp);
        //check if rook is on A1/A8 if white/black
        if(piece.color == ChessColor.WHITE){
            if(!(field.getPiece(0,0).color == ChessColor.WHITE && !field.getPiece(0, 0).moved)){
                removeMoveFromArraylist(theoreticalMoves, new int[]{-2,0});
            }
            if(!(field.getPiece(7,0).color == ChessColor.WHITE && !field.getPiece(7, 0).moved)){
                removeMoveFromArraylist(theoreticalMoves, new int[]{2,0});
            }
        }else{
            if(!(field.getPiece(0,7).color == ChessColor.BLACK && !field.getPiece(0, 7).moved)){
                removeMoveFromArraylist(theoreticalMoves, new int[]{-2,0});
            }
            if(!(field.getPiece(7,7).color == ChessColor.BLACK && !field.getPiece(7, 7).moved)){
                removeMoveFromArraylist(theoreticalMoves, new int[]{2,0});
            }
        }
    }

    private void generatePawnMoves(Piece piece){
        int vorzeichen = 1;
        if (piece.color == ChessColor.BLACK) {
            vorzeichen = -1;
        }
        ArrayList<int[]> pawnMoves = new ArrayList<>();
        int[][] temp = {{0,vorzeichen}, {0,2 * vorzeichen}};
        Collections.addAll(pawnMoves, temp);
        if (piece.moved) {
            removeMoveFromArraylist(pawnMoves, new int[]{0,2*vorzeichen});
        }
        piece.moves = pawnMoves;
    }
    private void generateKnightMoves(Piece piece){
        ArrayList<int[]> knightMoves = new ArrayList<>();
        int[][] temp = {
                {-2, 1}, {-1, 2}, {1, 2}, {2, 1},  // Rechts oben im Uhrzeigersinn
                {2, -1}, {1, -2}, {-1, -2}, {-2, -1} // Links unten im Uhrzeigersinn
        };
        Collections.addAll(knightMoves, temp);
        piece.moves = knightMoves;
    }

    private void removeMoveFromArraylist(ArrayList<int[]> list, int[] move){
        list.forEach((p)->{
            if(p==move){
                list.remove(p);
            }
        });
    }
}
