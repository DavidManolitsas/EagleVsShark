package main.java.model.move.shape;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2020-04-16
 */
public abstract class PaintShape
        implements PaintShapeI {

    protected List<int[]> paintInfo = new ArrayList<>();

    @Override
    public List<int[]> getPaintInfo() {
        return paintInfo;
    }
}
