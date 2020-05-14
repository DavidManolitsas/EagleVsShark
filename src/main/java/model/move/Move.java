package main.java.model.move;

import java.util.List;

import main.java.model.move.movements.Movements;
import main.java.model.move.shape.PaintShape;

/*
 * Precondition: valid movement and paintShape
 * Postcondition: valid move information
 */
public class Move {
    private Movements movements;
    private PaintShape paintShape;
    private boolean isPowered;

    public Move(Movements movements, PaintShape paintShape) {
        this.movements = movements;
        this.paintShape = paintShape;
        this.isPowered = false;
    }

    public PaintShape getPaintShape() {
        return paintShape;
    }

    public int[] getFinalPosition() {
        return movements.getDestination();
    }

    public List<int[]> getRoute() {
        return movements.getRoute();
    }

    public boolean isPowered() {
        return isPowered;
    }

    public void setPowered(boolean powered) {
        isPowered = powered;
    }

    @Override
    public String toString() {
        return movements.toString();
    }
}
