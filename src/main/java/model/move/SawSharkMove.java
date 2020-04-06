package main.java.model.move;

import java.util.List;

public class SawSharkMove extends Move {
    private int startRow;
    private int startCol;
    private int destRow;
    private int destCol;
    private List<Integer[]> paintInfo;
    private List<Integer[]> route;

    public SawSharkMove(int startRow, int startCol, int num_squares_moved, String direction) {
        super();
    }

    @Override
    public int[] getFinalPosition() {
        return super.getFinalPosition();
    }

    @Override
    public List<Integer[]> getRoute() {
        return route;
    }

    @Override
    public List<Integer[]> getPaintInfo() {
        return paintInfo;
    }
}
