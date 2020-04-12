package main.java.model.move;

import java.util.List;

public abstract class Move {
    protected final String PAINT = "paint";
    protected final String DEST = "dest";
    protected final String ROUTE = "route";
    protected final String[] COMMANDS = {DEST, PAINT, ROUTE};

    protected int startRow;
    protected int startCol;
    protected int[] destination = new int[2];
    protected int squaresMoved;
    protected String direction;
    protected List<int[]> paintInfo;
    protected List<int[]> route;

    protected Move(int startRow, int startCol, int squaresMoved, String direction){
        this.startRow = startRow;
        this.startCol = startCol;
        this.squaresMoved = squaresMoved;
        this.direction = direction;
    }

    public List<int[]> getPaintInfo(){
        return paintInfo;
    }

    public List<int[]> getRoute(){
        return route;
    }

    public int[] getFinalPosition(){
        return destination;
    }
}
