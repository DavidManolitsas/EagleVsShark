package main.java.model.move.shape;

/*
 * Precondition: none
 *              destination of the piece
 * Postcondition: a list of paint square locations
 */
public class CrossShape extends PaintShape {
    public CrossShape(int[] destination, boolean isPowered) {
        paintInfo.add(new int[] {destination[0] - 1, destination[1] - 1});
        paintInfo.add(new int[] {destination[0] - 1, destination[1] + 1});
        paintInfo.add(new int[] {destination[0] + 1, destination[1] - 1});
        paintInfo.add(new int[] {destination[0] + 1, destination[1] + 1});
        paintInfo.add(new int[] {destination[0], destination[1]});
        if (isPowered) {
            powerShape(destination);
        }

    }

    private void powerShape(int[] destination) {
        paintInfo.add(new int[] {destination[0] - 2, destination[1] - 2});
        paintInfo.add(new int[] {destination[0] - 2, destination[1] + 2});
        paintInfo.add(new int[] {destination[0] + 2, destination[1] - 2});
        paintInfo.add(new int[] {destination[0] + 2, destination[1] + 2});
    }
}
