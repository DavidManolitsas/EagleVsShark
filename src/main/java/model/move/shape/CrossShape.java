package main.java.model.move.shape;

import java.util.List;

/*
 * Precondition: none
 *              destination of the piece
 * Postcondition: a list of paint square locations
 */
public class CrossShape extends PaintShape {
    public CrossShape(int[] destination) {
        paintInfo.add(new int[] {destination[0] - 1, destination[1] - 1});
        paintInfo.add(new int[] {destination[0] - 1, destination[1] + 1});
        paintInfo.add(new int[] {destination[0] + 1, destination[1] - 1});
        paintInfo.add(new int[] {destination[0] + 1, destination[1] + 1});
        paintInfo.add(new int[] {destination[0], destination[1]});
    }
}
