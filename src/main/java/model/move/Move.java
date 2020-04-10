package main.java.model.move;

import java.util.List;

public abstract class Move {
    public static final String PAINT = "paint";
    public static final String DEST = "dest";
    public static final String ROUTE = "route";
    public static final String[] COMMANDS = {DEST, PAINT, ROUTE};
    protected boolean isValid;

    public abstract List<Integer[]> getPaintInfo();

    public abstract List<Integer[]> getRoute();

    public abstract int[] getFinalPosition();

    public abstract void checkValid();

    public boolean isValid(){
        return isValid;
    }
}
