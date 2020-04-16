package main.java.model.move;

import main.java.model.move.movements.Movements;
import main.java.model.move.shape.PaintShape;

import java.util.List;

public class Move {
    private Movements movements;
    private PaintShape paintShape;

    public Move(Movements movements, PaintShape paintShape) {
        this.movements = movements;
        this.paintShape = paintShape;
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
}
