package main.java.model.move;

import java.util.List;

public class GoldenEagleMove extends Move {
    private int startRow;
    private int startCol;
    private int destRow;
    private int destCol;
    private List<Integer[]> paintInfo;
    private List<Integer[]> route;

    @Override
    public int[] getFinalPosition() {
        return super.getFinalPosition();
    }

    @Override
    public List<Integer[]> getPaintInfo() {
        return paintInfo;
    }

    @Override
    public List<Integer[]> getRoute() {
        return route;
    }
}