package main.java.model.move;

import java.util.List;

public class BaldEagleMove extends Move {
    private int startRow;
    private int startCol;
    private int destRow;
    private int destCol;
    private List<Integer[]> paintInfo;
    private List<Integer[]> route;

    public BaldEagleMove(int startRow, int startCol, int squaresMoved, String direction) {
        super();
    }

    @Override
    public List<Integer[]> getPaintInfo() {
        return paintInfo;
    }

    @Override
    public int[] getFinalPosition() {
        return super.getFinalPosition();
    }

    @Override
    public List<Integer[]> getRoute() {
        return route;
    }
}
