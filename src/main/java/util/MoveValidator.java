package main.java.util;

import main.java.model.move.Move;

import java.util.ArrayList;
import java.util.List;

import static main.java.model.Board.COLUMN;
import static main.java.model.Board.ROW;

/**
 * @author WeiYi Yu
 * @date 2020-04-14
 */
public class MoveValidator {

    public static List<Move> validateMoves(List<Move> moves) {
        List<Move> validatedList = new ArrayList<>();
        for (Move move : moves) {
            int[] finalPos = move.getFinalPosition();
            if (isValidPosition(finalPos)) {
                List<int[]> paintInfo = move.getPaintInfo();
                paintInfo.removeIf(child -> !isValidPosition(child));
                move.setPaintInfo(paintInfo);

                validatedList.add(move);
            }
        }
        return validatedList;
    }

    private static boolean isValidPosition(int[] position) {
        return position[0] >= 0 && position[0] < ROW &&
                position[1] >= 0 && position[1] < COLUMN;
    }

}
