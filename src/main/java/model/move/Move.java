package main.java.model.move;

import java.util.List;

abstract class Move {
    private int startRow;
    private int startCol;
    private int destRow;
    private int destCol;
    private List<Integer[]> paintInfo;
    private List<Integer[]> route;

    public List<Integer[]> getPaintInfo(){
        return paintInfo;
    }

    public List<Integer[]> getRoute(){
        return route;
    }

    public int[] getFinalPosition(){
        int[] finalPosition = {destRow, destCol};
        return finalPosition;
    }
}
