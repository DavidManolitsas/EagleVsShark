package main.java.model.move.movements;

import com.google.java.contract.Ensures;

import java.util.ArrayList;
import java.util.List;

/*
 * Precondition: none
 *              starting point of the piece, step that need to move
 * Postcondition: an int array of the destination
 */
public abstract class Movements
        implements MovementI {

    protected int[] startPosition = new int[2];
    protected int[] destination = new int[2];

    protected int squaresMoved;

    public Movements(int startRow, int startCol, int squaresMoved) {
        startPosition[0] = startRow;
        startPosition[1] = startCol;
        this.squaresMoved = squaresMoved;
    }

    @Override
    public int[] getDestination() {
        return destination;
    }

    @Override
    public int[] getStartPosition() {
        return startPosition;
    }

    @Ensures("route.size() == 2")
    @Override
    public List<int[]> getRoute() {
        List<int[]> route = new ArrayList<>();
        int[] startPosition = getStartPosition();
        int[] destination = getDestination();
        route.add(startPosition);
        route.add(destination);
        return route;
    }

    @Override
    public String toString() {
        if (squaresMoved > 1) {
            return String.format("%1s %2d squares", getClass().getSimpleName(), squaresMoved);
        } else {
            return getClass().getSimpleName();
        }
    }
}
