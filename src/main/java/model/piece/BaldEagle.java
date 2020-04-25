package main.java.model.piece;


import main.java.model.move.Move;
import main.java.model.move.MoveBuilder;
import main.java.model.move.shape.PaintShapeType;

import java.util.List;

public class BaldEagle
        extends Eagle {

    public BaldEagle(int startRow, int startCol) {
        super(startRow, startCol);
    }

    /**
     * Ensures:
     * moveList of BaldEagleMove objects
     */
    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        return new MoveBuilder(startRow, startCol, PaintShapeType.SQUARE)
                .addAvailableStep(1, 2, 3)
                .addMoveLeft()
                .addMoveRight()
                .addMoveDown()
                .build();
    }

}
