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
    }

    private static void generateRookMoves(Board field, Piece piece) {
        ArrayList<int[]> rookMoves = new ArrayList<>();
        // Add all
        for (int i = 1; i < 8; i++) {
            rookMoves.add(new int[]{i, 0});
            rookMoves.add(new int[]{-i, 0});
            rookMoves.add(new int[]{0, i});
            rookMoves.add(new int[]{0, -i});
        }
        // Remove OOBounds
        for (int[] move : rookMoves) {
             if (piece.posX + move[1] > 7 || piece.posX + move[1] < 0 || piece.posY + move[0] > 7 || piece.posY + move[0] < 0) {
                 removeMoveFromArraylist(rookMoves, move);
             }
        }
        // Remove if piece in the way
        for (int[] move : rookMoves) {
            if (move[0] != 0) {
                if (!isPathClearHor(field, piece, piece.posX + move[1])) {
                    removeMoveFromArraylist(rookMoves, move);
                }
            } else {
                if (!isPathClearVert(field, piece, piece.posY + move[0])) {
                    removeMoveFromArraylist(rookMoves, move);
                }
            }
        }
        // Remove is cant move
        for (int[] move : rookMoves) {
            if (!canPieceMoveTo(field, piece, piece.posX + move[1], piece.posY + move[0])) {
                removeMoveFromArraylist(rookMoves, move);
            }
        }
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

        piece.moves = rookMoves;
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

    private static void generateKingMoves(Board field, Piece piece) {
        ArrayList<int[]> theoreticalMoves = new ArrayList<>();
        int[][] temp = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-2, 0}, {2, 0}};
        Collections.addAll(theoreticalMoves, temp);
        // Check for OOBounds
        for (int[] move : theoreticalMoves) {
            if (piece.posX + move[1] > 7 || piece.posX + move[1] < 0 || piece.posY + move[0] > 7 || piece.posY + move[0] < 0) {
                removeMoveFromArraylist(theoreticalMoves, move);
            }
        }
        // Check for Valid Move
        for (int[] move : theoreticalMoves) {
            if(!canPieceMoveTo(field, piece, piece.posY + move[0], piece.posY + move[1])) {
                removeMoveFromArraylist(theoreticalMoves, move);
            }
        }
        //check if rook is on A1/A8 if white/black
        if (piece.color == ChessColor.WHITE) {
            if (!(field.getPiece(0, 0).color == ChessColor.WHITE && !field.getPiece(0, 0).moved)) {
                removeMoveFromArraylist(theoreticalMoves, new int[]{-2, 0});
            }
            if (!(field.getPiece(7, 0).color == ChessColor.WHITE && !field.getPiece(7, 0).moved)) {
                removeMoveFromArraylist(theoreticalMoves, new int[]{2, 0});
            }
        } else {
            if (!(field.getPiece(0, 7).color == ChessColor.BLACK && !field.getPiece(0, 7).moved)) {
                removeMoveFromArraylist(theoreticalMoves, new int[]{-2, 0});
            }
            if (!(field.getPiece(7, 7).color == ChessColor.BLACK && !field.getPiece(7, 7).moved)) {
                removeMoveFromArraylist(theoreticalMoves, new int[]{2, 0});
            }
        }
        // Check for Check
        ChessColor otherColor = piece.color == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE;
        for (int[] move : theoreticalMoves) {
            if (field.isFieldAttackedBy(otherColor, piece.posX + move[0], piece.posY + move[1])) {
                removeMoveFromArraylist(theoreticalMoves, move);
            }
        }

        piece.moves=theoreticalMoves;
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
        // Remove OOBounds
        for (int[] move : theoreticalMoves) {
            if (piece.posX + move[1] > 7 || piece.posX + move[1] < 0 || piece.posY + move[0] > 7 || piece.posY + move[0] < 0) {
                removeMoveFromArraylist(theoreticalMoves, move);
            }
        }
        // Remove if piece in the way
        for (int[] move : theoreticalMoves) {
            if (move[0] != 0) {
                if (!isPathClearHor(field, piece, piece.posX + move[1])) {
                    removeMoveFromArraylist(theoreticalMoves, move);
                }
            } else {
                if (!isPathClearVert(field, piece, piece.posY + move[0])) {
                    removeMoveFromArraylist(theoreticalMoves, move);
                }
            }
        }
        // Remove is cant move
        for (int[] move : theoreticalMoves) {
            if (!canPieceMoveTo(field, piece, piece.posX + move[1], piece.posY + move[0])) {
                removeMoveFromArraylist(theoreticalMoves, move);
            }
        }
        piece.moves=theoreticalMoves;
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
        // Remove OOBounds
        for (int[] move : theoreticalMoves) {
            if (piece.posX + move[1] > 7 || piece.posX + move[1] < 0 || piece.posY + move[0] > 7 || piece.posY + move[0] < 0) {
                removeMoveFromArraylist(theoreticalMoves, move);
            }
        }
        // Remove if piece in the way
        for (int[] move : theoreticalMoves) {
            if (!isPathClearDiag(field, piece, piece.posX + move[1], piece.posY + move[0])) {
                removeMoveFromArraylist(theoreticalMoves, move);
            }
        }
        // Remove is cant move
        for (int[] move : theoreticalMoves) {
            if (!canPieceMoveTo(field, piece, piece.posX + move[1], piece.posY + move[0])) {
                removeMoveFromArraylist(theoreticalMoves, move);
            }
        }
        piece.moves = theoreticalMoves;
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
        // Check for OOBounds
        for (int[] move : pawnMoves) {
            if (piece.posX + move[1] > 7 || piece.posX + move[1] < 0 || piece.posY + move[0] > 7 || piece.posY + move[0] < 0) {
                removeMoveFromArraylist(pawnMoves, move);
            }
        }
        //Check if piece in the way
        for (int[] move : pawnMoves) {
            if (move[1] == 0 && !isPathClearVert(field, piece, piece.posY + move[0])) {
                removeMoveFromArraylist(pawnMoves, move);
            }
        }
        // Check for Valid Move
        for (int[] move : pawnMoves) {
            if (!canPieceMoveTo(field, piece, piece.posX + move[1], piece.posY + move[0])) {
                removeMoveFromArraylist(pawnMoves, move);
            }
        }

        piece.moves = pawnMoves;
    }

    private static void generateKnightMoves(Board field, Piece piece) {
        ArrayList<int[]> knightMoves = new ArrayList<>();
        int[][] temp = {
                {-2, 1}, {-1, 2}, {1, 2}, {2, 1},  // Rechts oben im Uhrzeigersinn
                {2, -1}, {1, -2}, {-1, -2}, {-2, -1} // Links unten im Uhrzeigersinn
        };
        Collections.addAll(knightMoves, temp);
        // Check for OOBounds
        for (int[] move : knightMoves) {
            if (piece.posX + move[1] > 7 || piece.posX + move[1] < 0 || piece.posY + move[0] > 7 || piece.posY + move[0] < 0) {
                removeMoveFromArraylist(knightMoves, move);
            }
        }
        // Check for Valid Move
        for (int[] move : knightMoves) {
            if (!canPieceMoveTo(field, piece, piece.posX + move[1], piece.posY + move[0])) {
                removeMoveFromArraylist(knightMoves, move);
            }
        }
        piece.moves = knightMoves;
    }

    private static void removeMoveFromArraylist(ArrayList<int[]> list, int[] move) {
        list.forEach((p) -> {
            if (p == move) {
                list.remove(p);
            }
        });
    }

    public static  ChessColor isInCheck(Board field, ChessColor color) {
        ArrayList<Piece> pieces = new ArrayList<>();
        for (Piece[] row : field.board) {
            for (Piece piece : row) {
                if (piece != null && piece.color == color) {
                    pieces.add(piece);
                }
            }
        }
        for (Piece piece : pieces) {
            if (piece.piecetype == Piecetype.KING) {
                int[] pos = field.getPiecePos(piece);
                if (field.isFieldAttackedBy(color, pos[0], pos[1])) {
                    return color;
                }
            }
        }
        return null;
    }

    public static boolean canPieceMoveTo(Board field, Piece p, int y, int x) {
        if (field.board[y][x] == null) {
            return true;
        }
        return field.board[y][x].color != p.color;
    }
}
