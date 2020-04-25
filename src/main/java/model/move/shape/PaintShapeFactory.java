package main.java.model.move.shape;

import main.java.model.move.movements.*;

public class PaintShapeFactory {

    private Movements movements;

    public PaintShapeFactory(Movements movements) {
        this.movements = movements;
    }

    public PaintShape create(PaintShapeType type) {
        switch (type) {
        default:
        case CROSS:
            return new CrossShape(new int[] {movements.getDestination()[0], movements.getDestination()[1]});
        case SQUARE:
            return new SquareShape(new int[] {movements.getDestination()[0], movements.getDestination()[1]});
        case TRIANGLE:
            return new TriangleShape(movements.getDestination()[0],
                                     movements.getDestination()[1],
                                     getTriangleShapeDirection());
        case TSHAPE:
            return new TShape(new int[] {movements.getDestination()[0], movements.getDestination()[1]});
        case VSHAPE:
            return new VShape(new int[] {movements.getDestination()[0], movements.getDestination()[1]},
                              getVShapeDirection());
        }
    }

    private int getTriangleShapeDirection() {
        int direction = Integer.MAX_VALUE;
        if (movements instanceof MoveDiagonalLeft) {
            direction = TriangleShape.DIRECTION_LEFT;
        } else if (movements instanceof MoveDiagonalRight) {
            direction = TriangleShape.DIRECTION_RIGHT;
        }
        return direction;
    }

    private int[] getVShapeDirection() {
        int[] directions = null;
        if (movements instanceof MoveLeft) {
            directions = VShape.DIRECTION_LEFT;
        } else if (movements instanceof MoveRight) {
            directions = VShape.DIRECTION_RIGHT;
        } else if (movements instanceof MoveUp) {
            directions = VShape.DIRECTION_UP;
        } else if (movements instanceof MoveDown) {
            directions = VShape.DIRECTION_DOWN;
        }
        return directions;
    }
}
