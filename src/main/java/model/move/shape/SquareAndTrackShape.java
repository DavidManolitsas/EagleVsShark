package main.java.model.move.shape;

public class SquareAndTrackShape extends PaintShape{

    public SquareAndTrackShape(int[] destination, int[] startingPoint){
        // add the square part using the existing class
        paintInfo.addAll(new SquareShape(destination).getPaintInfo());
        // add the track it covers
        paintInfo.addAll(new TrackShape().getPaintInfo());
    }
}
