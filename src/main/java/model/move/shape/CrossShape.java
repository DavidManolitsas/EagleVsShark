package main.java.model.move.shape;

import java.util.List;

public class CrossShape extends PaintShape {
    public CrossShape(int[] destination) {
        paintInfo.add(new int[] {destination[0] - 1, destination[1] - 1});
        paintInfo.add(new int[] {destination[0] - 1, destination[1] + 1});
        paintInfo.add(new int[] {destination[0] + 1, destination[1] - 1});
        paintInfo.add(new int[] {destination[0] + 1, destination[1] + 1});
        paintInfo.add(new int[] {destination[0], destination[1]});
    }

    public List<int[]> getPaintInfo() {
        return paintInfo;
    }
}
