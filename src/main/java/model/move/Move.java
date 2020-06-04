package main.java.model.move;

import main.java.model.move.movements.Movements;
import main.java.model.move.shape.PaintShape;

import java.util.Collections;
import java.util.List;

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

    public List<int[]> getReverseRoute() {
        List<int[]> route = movements.getRoute();
        Collections.reverse(route);
        return route;
    }

    public Movements getMovements() {
        return movements;
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
