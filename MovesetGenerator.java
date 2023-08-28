import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class MovesetGenerator {
    private Board field;

    public void generateMoves(Piece piece, Board field) {
        field = field;
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
        for (int[] moves: piece.moves) {
            if(!canPieceMoveTo(piece, piece.posX + moves[0], piece.posY + moves[1])) {
                removeMoveFromArraylist(piece.moves, moves);
            }
        }
    }

    private void generateKingMoves(Piece piece) {
        ArrayList<int[]> theoreticalMoves = new ArrayList<>();
        int[][] temp = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-2, 0}, {2, 0}};
        Collections.addAll(theoreticalMoves, temp);
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
    }

    private void generatePawnMoves(Piece piece) {
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
    }

    private void generateKnightMoves(Piece piece) {
        ArrayList<int[]> knightMoves = new ArrayList<>();
        int[][] temp = {
                {-2, 1}, {-1, 2}, {1, 2}, {2, 1},  // Rechts oben im Uhrzeigersinn
                {2, -1}, {1, -2}, {-1, -2}, {-2, -1} // Links unten im Uhrzeigersinn
        };
        Collections.addAll(knightMoves, temp);
        piece.moves = knightMoves;
    }

    private void removeMoveFromArraylist(ArrayList<int[]> list, int[] move) {
        list.forEach((p) -> {
            if (p == move) {
                list.remove(p);
            }
        });
    }

    private int[] checkHowFarPiececCanMove(Piece piece, int[] direction) {
        //if diagonal else horizontal/vertical
        if (direction[0] != 0 && direction[1] != 0) {
            //diagonal
            if (direction[0] > 0 && direction[1] > 0) {
                //to top right
                for (int i = 0; i < direction[0]; i++) {
                    if (piece.posX + i > 7 || piece.posY + i > 7) {
                        // oob
                        return new int[]{i - 1, i - 1};
                    }

                    if (field.getPiece(piece.posX + i, piece.posY + i) != null) {
                        //if white else black
                        if (piece.color == ChessColor.WHITE) {
                            if (field.getPiece(piece.posX + i, piece.posY + i).color == ChessColor.WHITE) {
                                //same color -> cant take piece
                                return new int[]{i - 1, i - 1};
                            } else {
                                return new int[]{i, i};
                            }
                        } else {
                            if (field.getPiece(piece.posX + i, piece.posY + i).color == ChessColor.BLACK) {
                                return new int[]{i - 1, i - 1};
                            } else {
                                return new int[]{i, i};
                            }
                        }

                    }
                }
            }

            if (direction[0] > 0 && direction[1] < 0) {
                //to down right
                for (int i = 0; i < direction[0]; i++) {
                    if (piece.posX + i > 7 || piece.posY - i > 7) {
                        // oob
                        return new int[]{i - 1, i + 1};
                    }

                    if (field.getPiece(piece.posX + i, piece.posY + i) != null) {
                        //if white else black
                        if (piece.color == ChessColor.WHITE) {
                            if (field.getPiece(piece.posX + i, piece.posY - i).color == ChessColor.WHITE) {
                                //same color -> cant take piece
                                return new int[]{i - 1, i + 1};
                            } else {
                                return new int[]{i, i};
                            }
                        } else {
                            if (field.getPiece(piece.posX + i, piece.posY - i).color == ChessColor.BLACK) {
                                return new int[]{i - 1, i + 1};
                            } else {
                                return new int[]{i, i};
                            }
                        }

                    }
                }

                if (direction[0] < 0 && direction[1] > 0) {
                    //to top left
                    for (int i = 0; i < -direction[0]; i++) {
                        if (piece.posX - i > 7 || piece.posY + i > 7) {
                            // oob
                            return new int[]{i + 1, i - 1};
                        }

                        if (field.getPiece(piece.posX - i, piece.posY + i) != null) {
                            //if white else black
                            if (piece.color == ChessColor.WHITE) {
                                if (field.getPiece(piece.posX - i, piece.posY + i).color == ChessColor.WHITE) {
                                    //same color -> cant take piece
                                    return new int[]{i + 1, i - 1};
                                } else {
                                    return new int[]{i, i};
                                }
                            } else {
                                if (field.getPiece(piece.posX - i, piece.posY + i).color == ChessColor.BLACK) {
                                    return new int[]{i + 1, i - 1};
                                } else {
                                    return new int[]{i, i};
                                }
                            }

                        }
                    }

                    if (direction[0] < 0 && direction[1] < 0) {
                        //to down left
                        for (int i = 0; i < -direction[0]; i++) {
                            if (piece.posX - i > 7 || piece.posY - i > 7) {
                                // oob
                                return new int[]{i + 1, i + 1};
                            }

                            if (field.getPiece(piece.posX - i, piece.posY - i) != null) {
                                //if white else black
                                if (piece.color == ChessColor.WHITE) {
                                    if (field.getPiece(piece.posX - i, piece.posY - i).color == ChessColor.WHITE) {
                                        //same color -> cant take piece
                                        return new int[]{i + 1, i + 1};
                                    } else {
                                        return new int[]{i, i};
                                    }
                                } else {
                                    if (field.getPiece(piece.posX - i, piece.posY - i).color == ChessColor.BLACK) {
                                        return new int[]{i + 1, i + 1};
                                    } else {
                                        return new int[]{i, i};
                                    }
                                }

                            }
                        }
                    } else {
                        //horizontal/vertical
                        if (direction[0] != 0) {
                            //horizontal rechts
                            for (int x = 0; x < 7; x++) {
                                if (field.getPiece(piece.posX + x, piece.posY + x) != null) {
                                    if (piece.posX + x > 7) {
                                        // oob
                                        return new int[]{x - 1, direction[1]};
                                    }
                                }
                            }

                        } else {
                            //vertical
                        }
                    }
                }
            }
        }
    }

    public ChessColor isInCheck(Board field, ChessColor color) {
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

    public boolean canPieceMoveTo(Piece p, int x, int y) {
        if (field.board[x][y] == null) {
            return true;
        }
        return field.board[x][y].color != p.color;
    }
}
