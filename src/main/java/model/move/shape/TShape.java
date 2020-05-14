package main.java.model.move.shape;

/**
 * @author WeiYi Yu
 * @date 2020-04-16
 */

/*
 * Precondition: none
 *              destination of the piece
 * Postcondition: a list of paint square locations
 */
public class TShape
        extends PaintShape {


    public TShape(int[] destination, int length, int width) {
        for (int row = destination[0]; row >= destination[0] - length; row--) {
            paintInfo.add(new int[]{row, destination[1]});
        }

        paintInfo.add(new int[] {destination[0] - length, destination[1] + width});
        paintInfo.add(new int[] {destination[0] - length, destination[1] - width});
        paintInfo.add(new int[] {destination[0] - length, destination[1] + width - 1});
        paintInfo.add(new int[] {destination[0] - length, destination[1] - width + 1});
    }
}
