package main.java.model.move.shape;

import java.util.List;

public class VShape extends PaintShape{

    // all direction is in the board's perspective

    public static final int[] DIRECTION_LEFT = {2, 3};
    public static final int[] DIRECTION_RIGHT = {1, 4};
    public static final int[] DIRECTION_UP = {1, 2};
    public static final int[] DIRECTION_DOWN = {3, 4};

    private static final int STAIRS = 3;

    public VShape(int[] destination, int[] direction) {
        int col = 0;
        for (int paintPart : direction){
            switch(paintPart) {
                case 1:
                    // right up
                    col = destination[1] + 1;
                    for (int row = destination[0] - 1; row >= destination[0] - STAIRS; row--, col++){
                        paintInfo.add(new int[] {row, col});
                    }
                    break;
                case 2:
                    col = destination[1] - 1;
                    for (int row = destination[0] - 1; row >= destination[0] - STAIRS; row--, col--){
                        paintInfo.add(new int[] {row, col});
                    }
                    break;
                case 3:
                    col = destination[1] - 1;
                    for (int row = destination[0] + 1; row <= destination[0] + STAIRS; row++, col--){
                        paintInfo.add(new int[] {row, col});
                    }
                    break;
                case 4:
                    col = destination[1] + 1;
                    for (int row = destination[0] + 1; row <= destination[0] + STAIRS; row++, col++){
                        paintInfo.add(new int[] {row, col});
                    }
                    break;
            }
        }

        paintInfo.add(new int[] {destination[0], destination[1]});
    }

    public List<int[]> getPaintInfo() {
        return paintInfo;
    }
}
