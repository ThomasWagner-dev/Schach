import figuren.*;

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
        if(piece.color == figuren.Piececolor.WHITE){
            if(!(field.getPiece(0,0).color == Piececolor.WHITE && field.getPiece(0,0).moved == false)){
                removeMoveFromArraylist(theoreticalMoves, new int[]{-2,0});
            }
            if(!(field.getPiece(7,0).color == Piececolor.WHITE && field.getPiece(7,0).moved == false)){
                removeMoveFromArraylist(theoreticalMoves, new int[]{2,0});
            }
        }else{
            if(!(field.getPiece(0,7).color == Piececolor.BLACK && field.getPiece(0,7).moved == false)){
                removeMoveFromArraylist(theoreticalMoves, new int[]{-2,0});
            }
            if(!(field.getPiece(7,7).color == Piececolor.BLACK && field.getPiece(7,7).moved == false)){
                removeMoveFromArraylist(theoreticalMoves, new int[]{2,0});
            }
        }
    }

    public int[][] generatePawnMoves(Piece piece, Board feld){
        //
        int[][] moves = new int[4][2];
        return moves;
    }

    private void removeMoveFromArraylist(ArrayList list, int[] move){
        list.forEach((p)->{
            if(p==move){
                list.remove(p);
            }
        });
    }
}
