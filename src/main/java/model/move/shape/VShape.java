package main.java.model.move.shape;

public class VShape
        extends PaintShape {

    // all direction is in the board's perspective

    public static final int[] DIRECTION_LEFT = {2, 3};
    public static final int[] DIRECTION_RIGHT = {1, 4};
    public static final int[] DIRECTION_UP = {1, 2};
    public static final int[] DIRECTION_DOWN = {3, 4};

    private static final int STAIRS = 3;

    public VShape(int[] destination, int[] direction, boolean isPowered) {
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
                    // left up
                case 2:
                    col = destination[1] - 1;
                    for (int row = destination[0] - 1; row >= destination[0] - STAIRS; row--, col--){
                        paintInfo.add(new int[] {row, col});
                    }
                    break;
                // bottom left
                case 3:
                    col = destination[1] - 1;
                    for (int row = destination[0] + 1; row <= destination[0] + STAIRS; row++, col--){
                        paintInfo.add(new int[] {row, col});
                    }
                    break;
                //bottom right
                case 4:
                    col = destination[1] + 1;
                    for (int row = destination[0] + 1; row <= destination[0] + STAIRS; row++, col++){
                        paintInfo.add(new int[] {row, col});
                    }
                    break;
            }
        }

        paintInfo.add(new int[] {destination[0], destination[1]});

        if (isPowered) {
            powerShape(destination, direction);
        }
    }

    void powerShape(int[] destination, int[] direction) {

        if (direction == DIRECTION_UP) {
            paintInfo.add(new int[] {destination[0] - 1, destination[1]});
            paintInfo.add(new int[] {destination[0] - 2, destination[1]});
            paintInfo.add(new int[] {destination[0] - 3, destination[1]});
        } else if (direction == DIRECTION_DOWN) {
            paintInfo.add(new int[] {destination[0] + 1, destination[1]});
            paintInfo.add(new int[] {destination[0] + 2, destination[1]});
            paintInfo.add(new int[] {destination[0] + 3, destination[1]});
        } else if (direction == DIRECTION_LEFT) {
            paintInfo.add(new int[] {destination[0], destination[1] - 1});
            paintInfo.add(new int[] {destination[0], destination[1] - 2});
            paintInfo.add(new int[] {destination[0], destination[1] - 3});
        } else if (direction == DIRECTION_RIGHT) {
            paintInfo.add(new int[] {destination[0], destination[1] + 1});
            paintInfo.add(new int[] {destination[0], destination[1] + 2});
            paintInfo.add(new int[] {destination[0], destination[1] + 3});
        }
    }

}
