package main.java.model.move.shape;

import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2020-04-16
 */
public class TShape
        extends PaintShape {

    private static final int LENGTH = 3;
    private static final int WIDTH = 1;

    public TShape(int[] destination) {
        for (int row = destination[0]; row >= destination[0] - LENGTH; row--) {
            paintInfo.add(new int[] {row, destination[1]});
        }
        paintInfo.add(new int[] {destination[0] - LENGTH, destination[1] + WIDTH});
        paintInfo.add(new int[] {destination[0] - LENGTH, destination[1] - WIDTH});
    }

    public List<int[]> getPaintInfo() {
        return paintInfo;
    }
}
