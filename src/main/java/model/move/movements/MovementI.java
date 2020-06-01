package main.java.model.move.movements;

import java.util.List;

/*
 * Precondition: none
 *              starting point of the piece, step that need to move
 * Postcondition: an int array of the destination
 */
public interface MovementI {

    int[] getDestination();

    int[] getStartPosition();

    List<int[]> getRoute();
}
