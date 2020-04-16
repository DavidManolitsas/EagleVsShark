package main.java.model.move.movements;

import java.util.ArrayList;
import java.util.List;

public class Movements {

    protected int[] startPosition = new int[2];
    protected int[] destination = new int[2];
    protected List<int[]> route = new ArrayList<>();

    public Movements(int startRow, int startCol) {
        startPosition[0] = startRow;
        startPosition[1] = startCol;
    }

    public int[] getDestination() {
        return destination;
    }

    public int[] getStartPosition() {
        return startPosition;
    }

    public List<int[]> getRoute() {
        List<int[]> route = new ArrayList<>();
        int[] startPosition = getStartPosition();
        int[] destination = getDestination();
        route.add(startPosition);
        route.add(destination);
        return route;
    }
}
