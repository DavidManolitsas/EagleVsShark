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
public class SquareShape
        extends PaintShape {

    // record the coordinates for the squares around the piece
    public SquareShape(int[] destination, boolean isPowered) {
        // paint the three squares at the top of the piece
        for (int col = destination[1] - 1; col <= destination[1] + 1; col++) {
            paintInfo.add(new int[]{destination[0] - 1, col});
        }
        // paint the three squares at the bottom of the piece
        for (int col = destination[1] - 1; col <= destination[1] + 1; col++) {
            paintInfo.add(new int[] {destination[0] + 1, col});
        }
        // paint the two squares on the left and right of the piece
        paintInfo.add(new int[] {destination[0], destination[1] - 1});
        paintInfo.add(new int[] {destination[0], destination[1] + 1});

        if (isPowered) {
            powerShape(destination);
        }
    }

    private void powerShape(int[] destination) {
        paintInfo.add(new int[] {destination[0], destination[1] - 2});
        paintInfo.add(new int[] {destination[0], destination[1] + 2});
        paintInfo.add(new int[] {destination[0] - 2, destination[1]});
        paintInfo.add(new int[] {destination[0] + 2, destination[1]});
    }
}
