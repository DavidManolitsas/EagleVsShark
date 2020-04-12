package main.java.model.move;

import java.util.List;

public class GoldenEagleMove extends Move {
    private int[] sharkPos = new int[2];

    public GoldenEagleMove(int startRow, int startCol, int[] sharkPos) {
        super(startRow, startCol, 0 , "");
        this.sharkPos = sharkPos;
        checkAndAttach();
    }

    private void checkAndAttach(){
        // determine destination based on the shark location
        destination[0] = sharkPos[0] + 1;
        destination[1] = sharkPos[1];

        // add starting point and destination into route
        route.add(new int[]{startRow, startCol});
        route.add(destination);

        // add paint area based on the destination and shark position

        // paint at the destination point
        paintInfo.add(new int[]{destination[0], destination[1]});
        // paint on the left and right side of the shark
        paintInfo.add(new int[]{sharkPos[0], sharkPos[1] - 1});
        paintInfo.add(new int[]{sharkPos[0], sharkPos[1] + 1});
        // paint on the left and right behind the destination
        paintInfo.add(new int[]{destination[0] + 1, destination[1] - 1});
        paintInfo.add(new int[]{destination[0] + 1, destination[1] + 1});
    }
}
