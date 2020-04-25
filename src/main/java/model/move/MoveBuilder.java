package main.java.model.move;

import main.java.model.move.movements.*;
import main.java.model.move.shape.PaintShape;
import main.java.model.move.shape.PaintShapeFactory;
import main.java.model.move.shape.PaintShapeType;

import java.util.ArrayList;
import java.util.List;

public class MoveBuilder {

    private int startRow;
    private int startCol;
    private PaintShapeType paintShapeType;

    protected int[] stepList;
    protected List<Move> moveList;

    public MoveBuilder(int startRow, int startCol, PaintShapeType paintShapeType) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.paintShapeType = paintShapeType;

        moveList = new ArrayList<>();
    }

    public MoveBuilder addAvailableStep(int... stepList) {
        this.stepList = stepList;
        return this;
    }

    public MoveBuilder addMoveLeft() {
        for (int step : stepList) {
            Movements movements = new MoveLeft(startRow, startCol, step);
            PaintShape paintShape = new PaintShapeFactory(movements).create(paintShapeType);
            moveList.add(new Move(movements, paintShape));
        }
        return this;
    }

    public MoveBuilder addMoveRight() {
        for (int step : stepList) {
            Movements movements = new MoveRight(startRow, startCol, step);
            PaintShape paintShape = new PaintShapeFactory(movements).create(paintShapeType);
            moveList.add(new Move(movements, paintShape));
        }
        return this;
    }

    public MoveBuilder addMoveUp() {
        for (int step : stepList) {
            Movements movements = new MoveUp(startRow, startCol, step);
            PaintShape paintShape = new PaintShapeFactory(movements).create(paintShapeType);
            moveList.add(new Move(movements, paintShape));
        }
        return this;
    }

    public MoveBuilder addMoveDown() {
        for (int step : stepList) {
            Movements movements = new MoveDown(startRow, startCol, step);
            PaintShape paintShape = new PaintShapeFactory(movements).create(paintShapeType);
            moveList.add(new Move(movements, paintShape));
        }
        return this;
    }

    public MoveBuilder addMoveDiagonalRight() {
        for (int step : stepList) {
            Movements movements = new MoveDiagonalRight(startRow, startCol, step);
            PaintShape paintShape = new PaintShapeFactory(movements).create(paintShapeType);
            moveList.add(new Move(movements, paintShape));
        }
        return this;
    }

    public MoveBuilder addMoveDiagonalLeft() {
        for (int step : stepList) {
            Movements movements = new MoveDiagonalLeft(startRow, startCol, step);
            PaintShape paintShape = new PaintShapeFactory(movements).create(paintShapeType);
            moveList.add(new Move(movements, paintShape));
        }
        return this;
    }

    public MoveBuilder addMoveBehindPiece(List<int[]> sharkPosList) {
        for (int[] positions : sharkPosList) {
            Movements movements = new MoveBehindPiece(startRow, startCol, positions[0], positions[1]);
            PaintShape paintShape = new PaintShapeFactory(movements).create(paintShapeType);
            moveList.add(new Move(movements, paintShape));
        }

        return this;
    }

    public List<Move> build() {
        return moveList;
    }

}
