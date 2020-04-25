package main.java.model.piece;

import main.java.model.move.Move;
import main.java.model.move.MoveBuilder;
import main.java.model.move.shape.PaintShapeType;

import java.util.List;

public class SawShark
        extends Shark {

    public SawShark(int startRow, int startCol) {
        super(startRow, startCol);
    }

    /**
     * Ensures:
     * moveList of SawSharkMove objects
     */
    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        return new MoveBuilder(startRow, startCol, PaintShapeType.TRIANGLE)
                .addAvailableStep(2)
                .addMoveLeft()
                .addMoveRight()
                .build();
    }

}
