package main.java.util;

import main.java.model.board.Board;
import main.java.model.move.Move;
import main.java.model.piece.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2020-05-25
 */
public class MoveValidator {

    public static List<Move> validateMoves(List<Move> moves, Piece movingPiece, Board board) {
        List<Move> validatedMoves = new ArrayList<>();
        for (Move move : moves) {
            int[] finalPos = move.getFinalPosition();

            if (BoardHelper.isSquareValid(finalPos, movingPiece, board)) {
                validatePaintInfo(move, board);
                validatedMoves.add(move);
            }
        }
        return validatedMoves;
    }

    public static void validatePaintInfo(Move move, Board board) {
        List<int[]> paintInfo = move.getPaintShape().getPaintInfo();
        paintInfo.removeIf(it -> BoardHelper.isPositionOutOfBound(it, board));
    }
}
