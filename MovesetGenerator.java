import java.util.ArrayList;
import java.util.Collections;

public class MovesetGenerator {

    public static void generateMoves(Board field, Piece piece) {
        switch (piece.piecetype) {
            case KING:
                generateKingMoves(field,piece);
                break;
            case QUEEN:
                generateQueenMoves(field,piece);
                break;
            case PAWN:
                generatePawnMoves(field,piece);
                break;
            case KNIGHT:
                generateKnightMoves(field,piece);
                break;
            case BISHOP:
                generateBishopMoves(field, piece);
                break;
            case ROOK:
                generateRookMoves(field, piece);
                break;
        }
        removeMovesThatLeadToCheck(field, piece);
    }

    private static void generateKingMoves(Board field, Piece piece) {
        ArrayList<int[]> theoreticalMoves = new ArrayList<>();
        int[][] temp = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-2, 0}, {2, 0}};
        Collections.addAll(theoreticalMoves, temp);
        piece.moves = theoreticalMoves;
        removeOOBounds(piece);
        removeBlockedMoves(field, piece);
        removeAllyAttacks(field, piece);
        //check if rook is on A1/A8 if white/black
        if(piece.moved!=true) {
            if (piece.color == ChessColor.WHITE) {
                if (!(field.getPiece(0, 0).color == ChessColor.WHITE && !field.getPiece(0, 0).moved)) {
                    removeMoveFromArraylist(piece.moves, new int[]{-2, 0});
                }
                if (!(field.getPiece(7, 0).color == ChessColor.WHITE && !field.getPiece(7, 0).moved)) {
                    removeMoveFromArraylist(piece.moves, new int[]{2, 0});
                }
                if (isPathClearHor(field,piece,0)){
                    removeMoveFromArraylist(piece.moves, new int[]{-2, 0});
                }
                if (isPathClearHor(field,piece,7)){
                    removeMoveFromArraylist(piece.moves, new int[]{2, 0});
                }
            } else {
                if (!(field.getPiece(0, 7).color == ChessColor.BLACK && !field.getPiece(0, 7).moved)) {
                    removeMoveFromArraylist(piece.moves, new int[]{-2, 0});
                }
                if (!(field.getPiece(7, 7).color == ChessColor.BLACK && !field.getPiece(7, 7).moved)) {
                    removeMoveFromArraylist(piece.moves, new int[]{2, 0});
                }
                if (isPathClearHor(field,piece,0)){
                    removeMoveFromArraylist(piece.moves, new int[]{-2, 0});
                }
                if (isPathClearHor(field,piece,7)){
                    removeMoveFromArraylist(piece.moves, new int[]{2, 0});
                }
            }
        }
        // Check for Check
        ChessColor otherColor = piece.color == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE;
        for (int[] move : piece.moves) {
            if (field.isFieldAttackedBy(otherColor, piece.posX + move[0], piece.posY + move[1])) {
                removeMoveFromArraylist(piece.moves, move);
            }
        }
    }

    private static void generateQueenMoves(Board field, Piece piece){
        ArrayList<int[]> theoreticalMoves = new ArrayList<>();
        //add up moves
        for(int y = 0; y<7; y++){
            theoreticalMoves.add(new int[]{y, 0});
        }
        //add down moves
        for(int y = 0; y<7; y++){
            theoreticalMoves.add(new int[]{-y, 0});
        }
        //add right moves
        for(int x = 0; x<7; x++){
            theoreticalMoves.add(new int[]{0,x});
        }
        //add left moves
        for(int x = 0; x<7; x++){
            theoreticalMoves.add(new int[]{0,-x});
        }
        //add up right moves
        for(int i = 0; i<7; i++){
            theoreticalMoves.add(new int[]{i,i});
        }
        //add down right moves
        for(int i = 0; i<7; i++){
            theoreticalMoves.add(new int[]{-i,i});
        }
        //add down left moves
        for(int i = 0; i<7; i++){
            theoreticalMoves.add(new int[]{-i,-i});
        }
        //add up left moves
        for(int i = 0; i<7; i++){
            theoreticalMoves.add(new int[]{i,-i});
        }
        piece.moves = theoreticalMoves;
        removeOOBounds(piece);
        removeBlockedMoves(field, piece);
        removeAllyAttacks(field, piece);
    }

    private static void generatePawnMoves(Board field, Piece piece) {
        int vorzeichen = 1;
        if (piece.color == ChessColor.BLACK) {
            vorzeichen = -1;
        }
        ArrayList<int[]> pawnMoves = new ArrayList<>();
        int[][] temp = {{0, vorzeichen}, {0, 2 * vorzeichen}};
        Collections.addAll(pawnMoves, temp);
        if (piece.moved) {
            removeMoveFromArraylist(pawnMoves, new int[]{0, 2 * vorzeichen});
        }
        piece.moves = pawnMoves;
        removeOOBounds(piece);
        removeBlockedMoves(field, piece);
        removeAllyAttacks(field, piece);
    }

    private static void generateKnightMoves(Board field, Piece piece) {
        ArrayList<int[]> theoreticalMoves = new ArrayList<>();
        int[][] temp = {
                {-2, 1}, {-1, 2}, {1, 2}, {2, 1},  // Rechts oben im Uhrzeigersinn
                {2, -1}, {1, -2}, {-1, -2}, {-2, -1} // Links unten im Uhrzeigersinn
        };
        Collections.addAll(theoreticalMoves, temp);
        piece.moves = theoreticalMoves;
        removeOOBounds(piece);
        removeAllyAttacks(field, piece);
    }

    private static void generateBishopMoves(Board field, Piece piece){
        ArrayList<int[]> theoreticalMoves = new ArrayList<>();
        //add up right moves
        for(int i = 0; i<7; i++){
            theoreticalMoves.add(new int[]{i,i});
        }
        //add down right moves
        for(int i = 0; i<7; i++){
            theoreticalMoves.add(new int[]{i,-i});
        }
        //add down left moves
        for(int i = 0; i<7; i++){
            theoreticalMoves.add(new int[]{-i,-i});
        }
        //add up left moves
        for(int i = 0; i<7; i++){
            theoreticalMoves.add(new int[]{-i,i});
        }
        piece.moves = theoreticalMoves;
        removeOOBounds(piece);
        removeBlockedMoves(field, piece);
        removeAllyAttacks(field, piece);
    }

    private static void generateRookMoves(Board field, Piece piece) {
        // Add all
        for (int i = 1; i < 8; i++) {
            piece.moves.add(new int[]{i, 0});
            piece.moves.add(new int[]{-i, 0});
            piece.moves.add(new int[]{0, i});
            piece.moves.add(new int[]{0, -i});
        }
        removeOOBounds(piece);
        removeBlockedMoves(field, piece);
        removeAllyAttacks(field, piece);
        //check if castle
        //check if rook is on A1/A8 if white/black
        /*if (piece.color == ChessColor.WHITE) {
            if (((piece.posX == 0 && piece.posY == 0)||(piece.posX == 8 && piece.posY == 0))&&piece.moved==false&&field.getPiece(4,0).moved==false) {
                rookMoves.add(new int[]{0,3});
            }
            if (!(field.getPiece(7, 0).color == ChessColor.WHITE && !field.getPiece(7, 0).moved)) {
                //removeMoveFromArraylist(theoreticalMoves, new int[]{2, 0});
            }
        } else {
            if (!(field.getPiece(0, 7).color == ChessColor.BLACK && !field.getPiece(0, 7).moved)) {
                //removeMoveFromArraylist(theoreticalMoves, new int[]{-2, 0});
            }
            if (!(field.getPiece(7, 7).color == ChessColor.BLACK && !field.getPiece(7, 7).moved)) {
                //removeMoveFromArraylist(theoreticalMoves, new int[]{2, 0});
            }
        }*/
    }

    public static boolean isPathClearHor(Board field, Piece piece, int x) {
        if (piece.posX < x) {
            for (int i = piece.posX + 1; i < x; i++) {
                if (field.board[piece.posY][i] != null) {
                    return false;
                }
            }
        } else {
            for (int i = piece.posX - 1; i > x; i--) {
                if (field.board[piece.posY][i] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isPathClearVert(Board field, Piece p, int y) {
        if (p.posY < y) {
            for (int i = p.posY + 1; i < y; i++) {
                if (field.board[i][p.posX] != null) {
                    return false;
                }
            }
        } else {
            for (int i = p.posY - 1; i > y; i--) {
                if (field.board[i][p.posX] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public static  boolean isPathClearDiag(Board field, Piece p, int x, int y) {
        if (p.posX > x) {
            if (p.posY > y) {
                for (int i = 1; p.posX + i < x; i++) {
                    if (field.board[p.posY + i][p.posX + i] != null) {
                        return false;
                    }
                }
            } else {
                for (int i = 1; p.posX + i < x; i++) {
                    if (field.board[p.posY - i][p.posX + i] != null) {
                        return false;
                    }
                }
            }
        } else {
            if (p.posY > y) {
                for (int i = 1; p.posX - i > x; i++) {
                    if (field.board[p.posY + i][p.posX - i] != null) {
                        return false;
                    }
                }
            } else {
                for (int i = 1; p.posX - i > x; i++) {
                    if (field.board[p.posY - i][p.posX - i] != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static void removeAllyAttacks(Board field, Piece p) {
        ArrayList<int[]> movesToRemove = new ArrayList<>();

        for (int[] move : p.moves) {
            if (!isAllyPiece(field, p, p.posX + move[1], p.posY + move[0])) {
                movesToRemove.add(move);
            }
        }
        for (int[] move : movesToRemove) {
            p.moves.remove(move);
        }
    }

    private static void removeBlockedMoves(Board field, Piece p) {
        ArrayList<int[]> movesToRemove = new ArrayList<>();
        for (int[] move : p.moves) {
            if (move[1] == 0 && !isPathClearVert(field, p, p.posY + move[0])) {
                movesToRemove.add(move);
            }
            if (move[0] == 0 && !isPathClearHor(field, p, p.posX + move[1])) {
                movesToRemove.add(move);
            }
            if (move[0] != 0 && move[1] != 0 && !isPathClearDiag(field, p, p.posX + move[1], p.posY + move[0])) {
                movesToRemove.add(move);
            }
        }
        for (int[] move : movesToRemove) {
            p.moves.remove(move);
        }
    }

    public static void removeOOBounds(Piece p) {
        ArrayList<int[]> movesToRemove = new ArrayList<>();
        for (int[] move : p.moves) {
            if (p.posX + move[1] > 7 || p.posX + move[1] < 0 || p.posY + move[0] > 7 || p.posY + move[0] < 0) {
                movesToRemove.add(move);
            }
        }
        for (int[] move : movesToRemove) {
            p.moves.remove(move);
        }
    }

    public static void removeMovesThatLeadToCheck(Board field, Piece p){
        ArrayList<int[]> movesToRemove = new ArrayList<>();
        for (int[] move : p.moves) {
            if (wouldMoveLeadToCheck(field, p.color, p, p.posY + move[0], p.posX + move[1])) {
                movesToRemove.add(move);
            }
        }
        for (int[] move : movesToRemove) {
            p.moves.remove(move);
        }
    }

    public static void removeIllegalMovesInCheck(Board field, ChessColor chessColor) {
        ArrayList<Piece> pieces = new ArrayList<>();
        for (Piece[] row : field.board) {
            for (Piece piece : row) {
                if (piece != null && piece.color == chessColor) {
                    pieces.add(piece);
                }
            }
        }
        for (Piece piece : pieces) {
            ArrayList<int[]> movesToRemove = new ArrayList<>();
            for (int[] move : piece.moves) {
                Board temp = new Board();
                temp.board = field.board.clone();
                temp.setField(piece.posY, piece.posX, null);
                temp.setField(piece.posY + move[0], piece.posX + move[1], piece);
                if (isInCheck(temp) == chessColor) {
                    movesToRemove.add(move);
                }
            }
            for (int[] move : movesToRemove) {
                piece.moves.remove(move);
            }
        }
    }

    private static void removeMoveFromArraylist(ArrayList<int[]> list, int[] move) {
        ArrayList<int[]> movesToRemove = new ArrayList<>();
        list.forEach((p) -> {
            if (p == move) {
                movesToRemove.add(p);
            }
        });
        for (int[] p : movesToRemove) {
            list.remove(p);
        }
    }

    public static  ChessColor isInCheck(Board field) {
        ArrayList<Piece> whitePieces = new ArrayList<>();
        ArrayList<Piece> blackPieces = new ArrayList<>();
        for (Piece[] row : field.board) {
            for (Piece piece : row) {
                if (piece != null && piece.color == ChessColor.WHITE) {
                    whitePieces.add(piece);
                }
            }
        }
        for (Piece[] row : field.board) {
            for (Piece piece : row) {
                if (piece != null && piece.color == ChessColor.BLACK) {
                    blackPieces.add(piece);
                }
            }
        }
        for (Piece piece : whitePieces) {
            if (piece.piecetype == Piecetype.KING) {
                if (field.isFieldAttackedBy(ChessColor.BLACK, piece.posY, piece.posX)) {
                    return ChessColor.WHITE;
                }
            }
        }
        for (Piece piece : blackPieces) {
            if (piece.piecetype == Piecetype.KING) {
                if (field.isFieldAttackedBy(ChessColor.WHITE, piece.posY, piece.posX)) {
                    return ChessColor.BLACK;
                }
            }
        }
        return null;
    }

    public static boolean wouldMoveLeadToCheck(Board field, ChessColor color, Piece p, int y, int x) {
        Board temp = new Board();
        temp.board = field.board.clone();
        temp.setField(p.posY, p.posX, null);
        temp.setField(y, x, p);
        return isInCheck(temp) == color;
    }

    public static boolean isAllyPiece(Board field, Piece p, int y, int x) {
        if (field.board[y][x] == null) {
            return true;
        }
        return field.board[y][x].color != p.color;
    }
}