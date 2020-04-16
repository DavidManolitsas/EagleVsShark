package main.java.model.move.shape;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2020-04-16
 */
public abstract class PaintShape {

    protected List<int[]> paintInfo = new ArrayList<>();

    public List<int[]> getPaintInfo() {
        return paintInfo;
    }
}