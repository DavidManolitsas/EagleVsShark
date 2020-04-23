package main.java.model.move.shape;

import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2020-04-16
 */

/*
 * Precondition: none
 *              starting point of the piece, direction of the move
 * Postcondition: a list of paint square locations
 */
public class TriangleShape
        extends PaintShape {


    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_LEFT = -1;

    private static final int WIDTH = 3;

    public TriangleShape(int startRow, int startCol, int direction) {
        for (int i = 0; i < WIDTH; i++) {
            for (int m = i; m > -1; m--) {
                paintInfo.add(new int[] {startRow - i, startCol + m * direction});
            }
        }
    }
}
