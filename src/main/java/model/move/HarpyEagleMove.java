package main.java.model.move;

import java.util.List;

public class HarpyEagleMove extends Move {
    private int startRow;
    private int startCol;
    private int destRow;
    private int destCol;
    private int squaresMoved;
    private String direction;
    private List<Integer[]> paintInfo;
    private List<Integer[]> route;

    public HarpyEagleMove(int startRow, int startCol, int squaresMoved, String direction) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.squaresMoved = squaresMoved;
        this.direction = direction;
    }

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
