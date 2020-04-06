package main.java.model.move;

import java.util.List;

public class GoldenEagleMove extends Move {
    private int startRow;
    private int startCol;
    private int destRow;
    private int destCol;
    private int[] sharkPos = new int[2];
    private List<Integer[]> paintInfo;
    private List<Integer[]> route;

    public GoldenEagleMove(int startRow, int startCol, int[] sharkPos) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.sharkPos = sharkPos;
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
